package com.inops.query.controller;


import com.inops.query.config.Util;
import com.inops.query.model.CriteriaRequest;
import com.inops.query.reactive.ReactiveMongoMusterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/query/attendance/muster")
@Log4j2
@RequiredArgsConstructor
public class ReactiveMongoMusterController {

    private final ReactiveMongoMusterService reactiveMongoMusterService;

    // Fetch all documents from a collection
    @GetMapping("/{collection}")
    public Flux<Document> fetchAll(@PathVariable String collection) {
        return reactiveMongoMusterService.findAll(collection).doOnSubscribe(subscription -> log.info("Query execution started for collection :{}",collection))
                .doOnNext (document ->{
                    if(document.get("_id")instanceof ObjectId){
                        document.put("_id",document.getObjectId("_id").toHexString());
                    }
                    log.info("Transformed document :{}",document);
                })
                .doOnError(error ->log.error("Error retrieving document:{}",error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found !!!")));
    }

    // Fetch a document by ID
    @GetMapping("/{collection}/{id}")
    public Mono<Document> fetchById(@PathVariable String collection, @PathVariable String id) {
        return reactiveMongoMusterService.findById(collection, id).doOnSubscribe(subscription -> log.info("Query execution started for collection :{}",collection))
                .doOnNext (document ->{
                    if(document.get("_id")instanceof ObjectId){
                        document.put("_id",document.getObjectId("_id").toHexString());
                    }
                    log.info("Transformed document :{}",document);
                })
                .doOnError(error ->log.error("Error retrieving document:{}",error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found !!!")));
    }

    // Fetch a document by ID
    @GetMapping("/{collection}/name/{name}")
    public Mono<Document> fetchByName(@PathVariable String collection, @PathVariable String name) {
        return reactiveMongoMusterService.findByName(collection, name).doOnSubscribe(subscription -> log.info("Query execution started for collection :{}",collection))
                .doOnNext (document ->{
                    if(document.get("_id")instanceof ObjectId){
                        document.put("_id",document.getObjectId("_id").toHexString());
                    }
                    log.info("Transformed document :{}",document);
                })
                .doOnError(error ->log.error("Error retrieving document:{}",error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found !!!")));
    }

    // Fetch documents with filters (query parameters)
    @GetMapping("/{collection}/search")
    public Flux<Document> fetchWithFilters(@PathVariable String collection, @RequestParam Map<String, String> filters) {
        Query query = new Query();
        filters.forEach((key, value) -> query.addCriteria(Criteria.where(key).is(value)));
        return reactiveMongoMusterService.findWithFilters(collection, query).doOnSubscribe(subscription -> log.info("Query execution started for collection :{}",collection))
                .doOnNext (document ->{
                    if(document.get("_id")instanceof ObjectId){
                        document.put("_id",document.getObjectId("_id").toHexString());
                    }
                    log.info("Transformed document :{}",document);
                })
                .doOnError(error ->log.error("Error retrieving document:{}",error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found !!!")));
    }

    @PostMapping(value = "/{collection}/search")
    public Flux<Document> fetchWithFilters(
            @PathVariable String collection,
            @RequestBody List<CriteriaRequest> criteriaRequests) {

        Query query = Util.getQuery(criteriaRequests);

        return reactiveMongoMusterService.findWithFilters(collection, query)
                .doOnSubscribe(subscription -> log.info("Query started for collection: {}", collection))
                .doOnNext(document -> {
                    if (document.get("_id") instanceof ObjectId) {
                        document.put("_id", document.getObjectId("_id").toHexString());
                    }
                    log.debug("Fetched document: {}", document);
                })
                .doOnError(error -> log.error("Mongo query error: {}", error.getMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No documents found in collection: " + collection)));
    }

    // Dynamic search by field and list of values
    @PostMapping("/{collection}/searchByField/{fieldName}")
    public Flux<Document> searchByFieldValues(
            @PathVariable String collection,
            @PathVariable String fieldName,
            @RequestBody List<Object> values) {
        return reactiveMongoMusterService.findByFieldValues(collection, fieldName, values).doOnSubscribe(subscription -> log.info("Query execution started for collection :{}",collection))
                .doOnNext (document ->{
                    if(document.get("_id")instanceof ObjectId){
                        document.put("_id",document.getObjectId("_id").toHexString());
                    }
                    log.info("Transformed document :{}",document);
                })
                .doOnError(error ->log.error("Error retrieving document:{}",error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found !!!")));
    }

    // POST: /api/{collection}/searchByMultipleFields
    @PostMapping("/{collection}/searchByMultipleFields")
    public Flux<Document> searchByMultipleFieldValues(
            @PathVariable String collection,
            @RequestBody Map<String, List<Object>> fieldValuesMap) {
        return reactiveMongoMusterService.findByMultipleFieldValues(collection, fieldValuesMap).doOnSubscribe(subscription -> log.info("Query execution started for collection :{}",collection))
                .doOnNext (document ->{
                    if(document.get("_id")instanceof ObjectId){
                        document.put("_id",document.getObjectId("_id").toHexString());
                    }
                    log.info("Transformed document :{}",document);
                })
                .doOnError(error ->log.error("Error retrieving document:{}",error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found !!!")));
    }

}
