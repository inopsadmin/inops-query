package com.inops.query.controller;

import com.inops.query.collection.Attendance;
import com.inops.query.repository.AttendanceRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/query/attendance")
public class AttendanceQueryController {
    private final AttendanceRepository repository;

    public AttendanceQueryController(AttendanceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{employeeId}")
    public Flux<Attendance> getAttendance(@RequestHeader("X-Tenant") String tenant, @PathVariable String employeeId) {
        return repository.findByTenantAndEmployeeId(tenant, employeeId);
    }
}