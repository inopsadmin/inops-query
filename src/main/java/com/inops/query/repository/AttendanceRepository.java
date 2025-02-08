package com.inops.query.repository;

import com.inops.query.collection.Attendance;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AttendanceRepository  extends ReactiveMongoRepository<Attendance, String> {

    Flux<Attendance> findByTenantAndEmployeeId(String tenant, String employeeId);
}

