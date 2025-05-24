package com.inops.query.config;


import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;


@Configuration
public class MongoConfig {

    @Bean("reactiveMongoTemplate")
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(MongoClients.create("mongodb://admin:password@49.206.252.89:27017/ContractLabour?authSource=admin"), "ContractLabour");
    }

    @Bean("reactiveMusterMongoTemplate")
    public ReactiveMongoTemplate reactiveMusterMongoTemplate() {
        return new ReactiveMongoTemplate(MongoClients.create("mongodb://admin:password@49.206.252.89:27017/MusterPunchData?authSource=admin"), "MusterPunchData");
    }
}