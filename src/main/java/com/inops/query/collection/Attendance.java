package com.inops.query.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "attendance")
public class Attendance {
    @Id
    private String id;
    private String tenant;         // Multi-tenant identifier
    private String employeeId;
    private String eventType;
    private LocalDateTime timestamp;
}