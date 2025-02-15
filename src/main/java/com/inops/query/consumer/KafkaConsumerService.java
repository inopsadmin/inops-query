package com.inops.query.consumer;

import com.inops.query.collection.Attendance;
import com.inops.query.model.AttendanceEvent;
import com.inops.query.repository.AttendanceRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class KafkaConsumerService {
    private final AttendanceRepository repository;

    public KafkaConsumerService(AttendanceRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "cmd-events", groupId = "query-service")
    public void consumeAttendanceEvent(AttendanceEvent event,@Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        System.out.println("Received event from Kafka: " + event);

        Attendance attendance = new Attendance();
        attendance.setTenant(event.getTenant());
        attendance.setEmployeeId(event.getEmployeeId());
        attendance.setEventType(event.getEventType());
       // attendance.setTimestamp(event.getTimestamp());

        repository.save(attendance)
                .doOnSuccess(a -> System.out.println("Saved to MongoDB: " + a))
                .subscribe();
    }
}