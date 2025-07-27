package com.em.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import employee.events.EmployeeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {


    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "employee", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            EmployeeEvent employeeEvent = EmployeeEvent.parseFrom(event);
            // ... perform business logic

            log.info("Received employee event: [employeeId={}, employeeName={}, employeeEmail={}", employeeEvent.getEmployeeId(), employeeEvent.getName(), employeeEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing event {}", e.getMessage());
        }
    }
}
