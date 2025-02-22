package com.inops.query;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

public class KafkaConsumerRoute {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new KafkaConsumer());
        main.run();
    }

    static class KafkaConsumer extends RouteBuilder {
        @Override
        public void configure() throws Exception {
            from("kafka:cmd-events?brokers=localhost:9092&groupId=query-service")
                    .log("Received message from Kafka: ${body}")
                    .process(exchange -> {
                        // Process the Kafka message here
                        String message = exchange.getIn().getBody(String.class);
                        System.out.println("Processing message: " + message);
                    });
        }
    }
}
