package com.inops.query.controller;

import com.inops.query.model.DocumentResponse;
import com.inops.query.reactive.ClassMongoService;
import com.inops.query.reactive.ReactiveMongoService;
import com.inops.query.record.Employee;
import com.inops.query.record.FileDetails;
import com.inops.query.record.Organization;
import com.inops.query.record.Workflow;
import com.inops.query.record.WorkflowManagement;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private final ClassMongoService classMongoService;

    @QueryMapping
    public Flux<DocumentResponse> fetchAllDocuments(@Argument("collection") String collection) {
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
    public Flux<Employee> fetchAllEmployees(@Argument("collection") String collection) {
        return classMongoService.findAll(collection,Employee.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<Employee> getEmployeeById(@Argument("id") String id, @Argument("collection") String collection) {
        return classMongoService.findById(collection, id, Employee.class).doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Employee> getAllEmployeeByIds(@Argument("id") List<String> id, @Argument("collection") String collection){
        Query query = new Query(Criteria.where("_id").in(id));
        return classMongoService.findWithFilters(collection, query, Employee.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Workflow> fetchAllWorkflows(@Argument("collection") String collection){
        return classMongoService.findAll(collection, Workflow.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for Collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", collection))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Workflow> getAllWorkflowByIds(@Argument("id") List<String> id, @Argument("collection") String collection){
        Query query = new Query(Criteria.where("_id").in(id));
        return classMongoService.findWithFilters(collection, query, Workflow.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<Workflow> getWorkflowById(@Argument("id") String id, @Argument("collection") String collection){
        return classMongoService.findById(collection, id, Workflow.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<WorkflowManagement> fetchAllWorkflowManagements(@Argument("collection") String collection){
        return classMongoService.findAll(collection, WorkflowManagement.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", collection))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<WorkflowManagement> getAllWorkflowManagementByIds(@Argument("id") List<String> id, @Argument("collection") String collection){
        Query query = new Query(Criteria.where("_id").in(id));
        return classMongoService.findWithFilters(collection, query, WorkflowManagement.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<WorkflowManagement> getWorkflowManagementById(@Argument("id") String id, @Argument("collection") String collection){
        return classMongoService.findById(collection, id, WorkflowManagement.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<FileDetails> fetchAllFileDetails(@Argument("collection") String collection){
        return classMongoService.findAll(collection, FileDetails.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", collection))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<FileDetails> getAllFileDetailsByIds(@Argument("id") List<String> id, @Argument("collection") String collection){
        Query query = new Query(Criteria.where("_id").in(id));
        return classMongoService.findWithFilters(collection, query, FileDetails.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<FileDetails>   getFileDetailsById(@Argument("id") String id, @Argument("collection") String collection){
        return classMongoService.findById(collection, id, FileDetails.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Organization> fetchAllOrganization(@Argument("collection") String collection){
        return classMongoService.findAll(collection, Organization.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", collection))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Organization> getAllOrganizationByIds(@Argument("id") List<String> id, @Argument("collection") String collection){
        Query query = new Query(Criteria.where("_id").in(id));
        return classMongoService.findWithFilters(collection, query, Organization.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<Organization>   getOrganizationsById(@Argument("id") String id, @Argument("collection") String collection){
        return classMongoService.findById(collection, id, Organization.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }
}
