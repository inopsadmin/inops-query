package com.inops.query.config;


import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;


@Configuration
public class MongoConfig {

    @Value("${db.host}")
    private String dbUri;

    @Bean("reactiveMongoTemplate")
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(MongoClients.create("mongodb://admin:password@"+dbUri+"/ContractLabour?authSource=admin"), "ContractLabour");
    }

    @Bean("reactiveMusterMongoTemplate")
    public ReactiveMongoTemplate reactiveMusterMongoTemplate() {
        return new ReactiveMongoTemplate(MongoClients.create("mongodb://admin:password@"+dbUri+"/MusterPunchData?authSource=admin"), "MusterPunchData");
    }
}