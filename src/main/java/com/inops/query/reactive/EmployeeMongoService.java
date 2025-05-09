package com.inops.query.reactive;

import com.inops.query.record.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("employeeMongoService")
@RequiredArgsConstructor
public class EmployeeMongoService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Flux<Employee> findAll(String collectionName) {
        return reactiveMongoTemplate.findAll(Employee.class, collectionName);
    }

    public Mono<Employee> findById(String collectionName, String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return reactiveMongoTemplate.findOne(query, Employee.class, collectionName);
    }
}
