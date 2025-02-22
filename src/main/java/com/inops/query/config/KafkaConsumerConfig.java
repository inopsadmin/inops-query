package com.inops.query.config;


import com.inops.query.camel.KafkaToReactiveMongoRoute;

import com.inops.query.model.KafkaEvent;
import lombok.RequiredArgsConstructor;
import org.apache.camel.main.Main;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {


  private KafkaToReactiveMongoRoute kafkaToReactiveMongoRoute;

    @Bean
    public ConsumerFactory<String, KafkaEvent> consumerFactory() {
        JsonDeserializer<KafkaEvent> deserializer = new JsonDeserializer<>(KafkaEvent.class);
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
    public ConcurrentKafkaListenerContainerFactory<String, KafkaEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

//    @Bean
//    @DependsOn("kafkaToReactiveMongoRoute")
//    public Main startCamel()
//    {
//        try {
//            Main main = new Main();
//            main.configure().addRoutesBuilder(kafkaToReactiveMongoRoute);
//            main.run();
//            return main;
//        }catch (Exception e)
//        {
//            System.out.println(e);
//        }
//        return null;
//    }
}
