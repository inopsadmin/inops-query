package com.inops.query.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class AttendanceEvent {
    private String tenant;         // Multi-tenant identifier
    private String employeeId;
    private String eventType;      // CHECK_IN or CHECK_OUT
    private LocalDateTime timestamp;
}
