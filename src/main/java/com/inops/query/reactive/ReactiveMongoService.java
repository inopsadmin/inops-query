package com.inops.query.reactive;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.core.query.Update;

@Service
public class ReactiveMongoService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public ReactiveMongoService(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    // Fetch all documents from a collection
    public Flux<Object> findAll(String collectionName) {
        return reactiveMongoTemplate.findAll(Object.class, collectionName);
    }

    // Fetch a document by ID
    public Mono<Object> findById(String collectionName, String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return reactiveMongoTemplate.findOne(query, Object.class, collectionName);
    }

    // Fetch documents based on query parameters
    public Flux<Object> findWithFilters(String collectionName, Query query) {
        return reactiveMongoTemplate.find(query, Object.class, collectionName);
    }

    // Create a new document in a collection
    public Mono<Object> create(String collectionName, Object document) {
        return reactiveMongoTemplate.save(document, collectionName);
    }

    // Update a document in a collection by ID
    public Mono<Object> update(String collectionName, String id, Object updatedDocument) {
        Query query = new Query(Criteria.where("_id").is(id));
        return reactiveMongoTemplate.findAndModify(query, new Update().set("data", updatedDocument), Object.class, collectionName);
    }

    // Delete a document by ID
    public Mono<Void> delete(String collectionName, String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return reactiveMongoTemplate.remove(query, Object.class, collectionName).then();
    }
}