package com.inops.query.consumer;


import com.inops.query.model.KafkaEvent;
import com.inops.query.reactive.ReactiveMongoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class kafkaConsumer {

    private final ReactiveMongoService reactiveMongoService;
    private final Sinks.Many<KafkaEvent> sink = Sinks.many().multicast().onBackpressureBuffer();


    @KafkaListener(topics = "cmd-query", groupId = "query-services")
   public void consumeAttendanceEvent(KafkaEvent event, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
           System.out.println("Received event from Kafka: " + event);
        switch (event.getAction().toLowerCase()) {
            case "insert":
                System.out.println("calling mongo");
                reactiveMongoService.create(event.getCollectionName(), event.getData())
                        .doOnSuccess(result -> System.out.println("✅ Saved in MongoDB: " + result))
                        .doOnError(error -> System.err.println("❌ MongoDB Save Failed: " + error.getMessage()))
                        .subscribe();
                break;
            case "update":
                System.out.println("🔄 Calling MongoDB Update...");
                reactiveMongoService.update(event.getCollectionName(), event.getId(), event.getData())
                        .doOnSuccess(result -> System.out.println("✅ Successfully Updated: " + result))
                        .doOnError(error -> System.err.println("❌ Update Failed: " + error.getMessage()))
                        .subscribe();
                break;

            case "delete":
                System.out.println("🗑️ Calling MongoDB Delete...");
                reactiveMongoService.delete(event.getCollectionName(), event.getId())
                        .doOnSuccess(result -> System.out.println("✅ Successfully Deleted: " + result))
                        .doOnError(error -> System.err.println("❌ Delete Failed: " + error.getMessage()))
                        .subscribe();
                break;
            case "sse":
                sink.tryEmitNext(event);
                System.out.println("calling mongo");
                reactiveMongoService.create(event.getCollectionName(), event.getData())
                        .doOnSuccess(result -> System.out.println("✅ Saved in MongoDB: " + result))
                        .doOnError(error -> System.err.println("❌ MongoDB Save Failed: " + error.getMessage()))
                        .subscribe();
                break;
            default:
                System.err.println("⚠️ Unknown action: " + event.getAction());

        }

    }

    public Flux<KafkaEvent> getMessages() {
        return sink.asFlux();
    }
}
