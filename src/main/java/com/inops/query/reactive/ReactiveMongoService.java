package com.inops.query.reactive;

import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReactiveMongoService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public ReactiveMongoService(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    // Fetch all documents from a collection
    public Flux<Document> findAll(String collectionName) {
        return reactiveMongoTemplate.findAll(Document.class, collectionName);
    }

    // Fetch a document by ID
    public Mono<Document> findById(String collectionName, String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return reactiveMongoTemplate.findOne(query, Document.class, collectionName);
    }

    public Mono<Document> findByName(String collectionName, String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return reactiveMongoTemplate.findOne(query, Document.class, collectionName);
    }

    // Fetch documents based on query parameters
    public Flux<Document> findWithFilters(String collectionName, Query query) {
        return reactiveMongoTemplate.find(query, Document.class, collectionName);
    }

    // Find documents dynamically by any field and list of values
    public Flux<Document> findByFieldValues(String collection, String fieldName, List<Object> values) {
        Query query = new Query(Criteria.where(fieldName).in(values));
        return reactiveMongoTemplate.find(query, Document.class, collection);
    }

    public Flux<Document> findByMultipleFieldValues(String collection, Map<String, List<Object>> fieldValuesMap) {
        List<Criteria> criteriaList = fieldValuesMap.entrySet().stream()
                .map(entry -> Criteria.where(entry.getKey()).in(entry.getValue()))
                .collect(Collectors.toList());

        // Combine all criteria with AND operation
        Query query = new Query(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));

        return reactiveMongoTemplate.find(query, Document.class, collection);
    }

    public Flux<Document> findAllById(String collectionName, String id) {
        Aggregation matchAggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("operationType").is("insert")
                        .and("fullDocument._id").is(id))
        );

        ChangeStreamOptions options = ChangeStreamOptions.builder()
                .returnFullDocumentOnUpdate()
                .filter(matchAggregation)
                .build();

        return reactiveMongoTemplate.changeStream(collectionName, options, Document.class)
                .map(ChangeStreamEvent::getBody);
    }

    // Create a new document in a collection
    public Mono<Object> create(String collectionName, Object document) {
        return reactiveMongoTemplate.save(document,collectionName);
    }

    // Update a document in a collection by ID
    public Mono<Document> update(String collectionName, String id, Object updatedDocument) {
        Query query = new Query(Criteria.where("_id").is(id));
        return reactiveMongoTemplate.findAndModify(query, new Update().set("data", updatedDocument), Document.class, collectionName);
    }

    // Delete a document by ID
    public Mono<Void> delete(String collectionName, String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return reactiveMongoTemplate.remove(query, Document.class, collectionName).then();
    }
}