package com.inops.query.config;

import com.inops.query.model.AttendanceEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {



    @Bean
    public ConsumerFactory<String, AttendanceEvent> consumerFactory() {
        JsonDeserializer<AttendanceEvent> deserializer = new JsonDeserializer<>(AttendanceEvent.class);
        deserializer.addTrustedPackages("*");  // ✅ Allow deserialization from any package
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(true);  // ✅ Ensure correct type mapping
        //deserializer.setAcceptCaseInsensitiveProperties(true);

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");  // ✅ Start consuming from the earliest available message

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AttendanceEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AttendanceEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
