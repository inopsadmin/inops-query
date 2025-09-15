package com.inops.query.controller;

import com.inops.query.config.Util;
import com.inops.query.model.CriteriaRequest;
import com.inops.query.model.DocumentResponse;
import com.inops.query.record.*;
import com.inops.query.reactive.ClassMongoService;
import com.inops.query.reactive.ReactiveMongoService;


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
    public Flux<Employee> fetchAllEmployees(@Argument("collection") String collection, @Argument("tenantCode") String tenantCode) {
        Query query = new Query(Criteria.where("tenantCode").is(tenantCode));
        return classMongoService.findWithFilters(collection, query, Employee.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<Employee> getEmployeeByEmployeeID(@Argument("collection") String collection, @Argument("employeeID") String employeeID, @Argument("tenantCode") String tenantCode) {
        Query query = new Query(Criteria.where("employeeID").is(employeeID));
        if(tenantCode != null && !tenantCode.trim().isEmpty()) {
            query.addCriteria(Criteria.where("tenantCode").is(tenantCode));
        }
        return classMongoService.findWithFilters(collection, query, Employee.class).next()
                .flatMap(employee -> Mono.justOrEmpty(employee))
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Employee> getAllEmployeeByEmployeeIDs(@Argument("collection") String collection, @Argument("employeeID") List<String> employeeID, @Argument("tenantCode") String tenantCode){
        Query query = new Query(Criteria.where("employeeID").in(employeeID));
        if(tenantCode != null && !tenantCode.trim().isEmpty()) {
            query.addCriteria(Criteria.where("tenantCode").is(tenantCode));
        }
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
    public Flux<FileDetails> fetchAllFileDetails(@Argument("collection") String collection, @Argument("tenantCode") String tenantCode){
        Query query = new Query(Criteria.where("tenantCode").is(tenantCode));
        return classMongoService.findWithFilters(collection, query, FileDetails.class)
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
    public Flux<Organization> fetchAllOrganization(@Argument("collection") String collection, @Argument("tenantCode") String tenantCode){
        Query query = new Query(Criteria.where("tenantCode").is(tenantCode));
        return classMongoService.findWithFilters(collection, query, Organization.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", collection))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Organization> getAllOrganizationByCodes(@Argument("organizationCode") List<String> organizationCode, @Argument("tenantCode") String tenantCode, @Argument("collection") String collection){
        Query query = new Query(Criteria.where("organizationCode").in(organizationCode));
        if(tenantCode != null && !tenantCode.trim().isEmpty()) {
            query.addCriteria(Criteria.where("tenantCode").is(tenantCode));
        }
        return classMongoService.findWithFilters(collection, query, Organization.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<Organization> getOrganizationByCode(@Argument("organizationCode") String organizationCode, @Argument("tenantCode") String tenantCode, @Argument("collection") String collection){
        Query query = new Query(Criteria.where("organizationCode").is(organizationCode));
        if(tenantCode != null && !tenantCode.trim().isEmpty()) {
            query.addCriteria(Criteria.where("tenantCode").is(tenantCode));
        }
        return classMongoService.findWithFilters(collection, query, Organization.class).next()
                .flatMap(organization -> Mono.justOrEmpty(organization))
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<LeavePolicyRecord> fetchAllLeavePolicy(@Argument("collection") String collection){
        return classMongoService.findAll(collection, LeavePolicyRecord.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", collection))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<LeavePolicyRecord> getAllLeavePolicyByIds(@Argument("id") List<String> id, @Argument("collection") String collection){
        Query query = new Query(Criteria.where("_id").in(id));
        return classMongoService.findWithFilters(collection, query, LeavePolicyRecord.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<LeavePolicyRecord> getLeavePolicyById(@Argument("id") String id, @Argument("collection") String collection){
        return classMongoService.findById(collection, id, LeavePolicyRecord.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<EmployeeLeaveBalance> fetchAllLeaveBalance(@Argument("collection") String collection){
        return classMongoService.findAll(collection, EmployeeLeaveBalance.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", collection))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<EmployeeLeaveBalance> getAllLeaveBalanceByIds(@Argument("id") List<String> id, @Argument("collection") String collection){
        Query query = new Query(Criteria.where("_id").in(id));
        return classMongoService.findWithFilters(collection, query, EmployeeLeaveBalance.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Mono<EmployeeLeaveBalance> getLeaveBalanceById(@Argument("id") String id, @Argument("collection") String collection){
        return classMongoService.findById(collection, id, EmployeeLeaveBalance.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<ShiftConfig> fetchShifts(@Argument("criteriaRequests") List<CriteriaRequest> criteriaRequests, @Argument("collection") String collection) {
        Query query = Util.getQuery(criteriaRequests);
        return classMongoService.findWithFilters(collection, query, ShiftConfig.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Contractor> fetchContractors(@Argument("criteriaRequests") List<CriteriaRequest> criteriaRequests,  @Argument("collection") String collection) {
        Query query = Util.getQuery(criteriaRequests);
        return classMongoService.findWithFilters(collection, query, Contractor.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<CompanyEmployee> fetchCompanyEmployee(@Argument("criteriaRequests") List<CriteriaRequest> criteriaRequests,  @Argument("collection") String collection) {
        Query query = Util.getQuery(criteriaRequests);
        return classMongoService.findWithFilters(collection, query, CompanyEmployee.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Employee> fetchEmployees(@Argument("criteriaRequests") List<CriteriaRequest> criteriaRequests,  @Argument("collection") String collection) {
        Query query = Util.getQuery(criteriaRequests);
        return classMongoService.findWithFilters(collection, query, Employee.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }
    @QueryMapping
    public Flux<LeavePolicyRecord> fetchLeavePolicy(@Argument("collection") String collection, @Argument("criteriaRequests") List<CriteriaRequest> criteriaRequests){
        Query query = Util.getQuery(criteriaRequests);
        return classMongoService.findWithFilters(collection, query, LeavePolicyRecord.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }
    @QueryMapping
    public Flux<EmployeeLeaveBalance> fetchLeaveBalance(@Argument("collection") String collection, @Argument("criteriaRequests") List<CriteriaRequest> criteriaRequests){
        Query query = Util.getQuery(criteriaRequests);
        return classMongoService.findWithFilters(collection, query, EmployeeLeaveBalance.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }

    @QueryMapping
    public Flux<Workflow> fetchWorkflows(@Argument("collection") String collection, @Argument("criteriaRequests") List<CriteriaRequest> criteriaRequests){
        Query query = Util.getQuery(criteriaRequests);
        return classMongoService.findWithFilters(collection, query, Workflow.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }
    @QueryMapping
    public Flux<FileDetails> fetchFileDetails(@Argument("collection") String collection, @Argument("criteriaRequests") List<CriteriaRequest> criteriaRequests){
        Query query = Util.getQuery(criteriaRequests);
        return classMongoService.findWithFilters(collection, query, FileDetails.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }
    @QueryMapping
    public Flux<Organization> fetchOrganization(@Argument("collection") String collection, @Argument("criteriaRequests") List<CriteriaRequest> criteriaRequests){
        Query query = Util.getQuery(criteriaRequests);
        return classMongoService.findWithFilters(collection, query, Organization.class)
                .doOnSubscribe(subscription -> log.info("Query execution started for collection: {}", collection))
                .doOnError(error -> log.error("Error retrieving document: {}", error.getLocalizedMessage()))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .switchIfEmpty(Flux.error(new ResourceNotFoundException("No matching documents found!!!")));
    }


}
