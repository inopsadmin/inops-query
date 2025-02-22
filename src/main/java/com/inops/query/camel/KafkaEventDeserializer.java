package com.inops.query.camel;

import com.inops.query.model.KafkaEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class KafkaEventDeserializer implements Deserializer<KafkaEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    @Override
    public KafkaEvent deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, KafkaEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize KafkaEvent", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}
