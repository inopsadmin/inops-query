package com.inops.query.consumer;

import com.inops.query.collection.Attendance;
import com.inops.query.model.AttendanceEvent;
import com.inops.query.repository.AttendanceRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final AttendanceRepository repository;

    public KafkaConsumerService(AttendanceRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "attendance-events", groupId = "query-service")
    public void consumeAttendanceEvent(AttendanceEvent event) {
        Attendance attendance = new Attendance();
        attendance.setTenant(event.getTenant());
        attendance.setEmployeeId(event.getEmployeeId());
        attendance.setEventType(event.getEventType());
        attendance.setTimestamp(event.getTimestamp());
        repository.save(attendance).subscribe();
    }
}