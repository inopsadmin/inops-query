package com.inops.query.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceEvent {
    private String tenant;         // Multi-tenant identifier
    private String employeeId;
    private String eventType;      // CHECK_IN or CHECK_OUT
    private LocalDateTime timestamp;
}
