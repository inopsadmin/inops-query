package com.inops.query.controller;

import com.inops.query.model.DocumentResponse;
import com.inops.query.reactive.EmployeeMongoService;
import com.inops.query.reactive.ReactiveMongoService;
import com.inops.query.record.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MongoGraphQLController {

    private final ReactiveMongoService reactiveMongoService;
    private final EmployeeMongoService employeeMongoService;

    @QueryMapping
    public Flux<DocumentResponse> fetchAllDocuments(@Argument String collection) {
        return reactiveMongoService.findAll(collection)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .map(document -> {
                    if (document.get("_id") instanceof ObjectId) {
                        document.put("_id", document.getObjectId("_id").toHexString());
                    }
                    log.info("Transformed document: {}", document);
                    return new DocumentResponse(document.getObjectId("_id").toHexString(),document);
                })
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Employee> fetchAllEmployees(@Argument String collection) {
        return employeeMongoService.findAll(collection)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<Employee> getEmployeeById(@Argument String id, @Argument String collection) {
        return employeeMongoService.findById(collection, id).doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

}
