package com.inops.query.reactive;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("mongoService")
@RequiredArgsConstructor
public class ClassMongoService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public <T> Flux<T> findAll(String collection, Class<T> clazz) {
        return reactiveMongoTemplate.findAll(clazz, collection);
    }

    public <T> Mono<T> findById(String collection, String id, Class<T> clazz) {
        Query query = new Query(Criteria.where("_id").is(id));
        return reactiveMongoTemplate.findOne(query, clazz, collection);
    }
}
