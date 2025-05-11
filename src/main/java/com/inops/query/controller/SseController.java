package com.inops.query.controller;

import com.inops.query.consumer.kafkaConsumer;
import com.inops.query.model.KafkaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/query/attendance/sse")
@RequiredArgsConstructor
public class SseController {

    private final kafkaConsumer kafkaConsumer;


    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<KafkaEvent>> streamKafkaMessages() {
        return kafkaConsumer.getMessages()
                .map(msg -> ServerSentEvent.builder(msg).build());
    }
}