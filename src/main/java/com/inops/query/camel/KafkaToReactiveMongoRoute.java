package com.inops.query.camel;

import com.inops.query.model.KafkaEvent;
import com.inops.query.reactive.ReactiveMongoService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("kafkaToReactiveMongoRoute")
@RequiredArgsConstructor
public class KafkaToReactiveMongoRoute extends RouteBuilder {


    private final ReactiveMongoService reactiveMongoService;

    @Override
    public void configure() throws Exception {

            from("kafka:cmd-events?brokers=localhost:9092" +
                    "&valueDeserializer=com.inops.query.camel.KafkaEventDeserializer"+
                    "&additionalProperties.spring.json.trusted.packages=*")
                .log("Received Kafka message: ${body}")
                .process(exchange -> {
                    KafkaEvent event = exchange.getIn().getBody(KafkaEvent.class);
                    exchange.getIn().setHeader("operation", event.getAction());
                    exchange.getIn().setHeader("collectionName", event.getCollectionName());
                    exchange.getIn().setBody(event.getData());
                })
                .bean(this, "processMessage")
                .log("MongoDB Operation Completed: ${headers.operation} on collection: ${headers.collectionName}");
    }

    public Mono<Void> processMessage(Exchange exchange) {
        String operation = exchange.getIn().getHeader("operation", String.class);
        String collectionName = exchange.getIn().getHeader("collectionName", String.class);
        Object data = exchange.getIn().getBody();

        switch (operation.toLowerCase()) {
            case "insert":
                return reactiveMongoService.create(collectionName,data)
                        .doOnSuccess(result -> System.out.println("✅ Saved in MongoDB: " + result))
                        .doOnError(error -> System.err.println("MongoDB Save Failed: " + error.getMessage()))
                        .then();
            case "update":
               // return reactiveMongoService.update(collectionName,"",data);
            case "delete":
                //return reactiveMongoService.delete(collectionName,"");
            default:
                return Mono.empty();
        }
    }
}