package com.em.employeeservice.kafka;

import com.em.employeeservice.model.Employee;
import employee.events.EmployeeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class kafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(kafkaProducer.class);
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public kafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Employee employee) {
        EmployeeEvent event = EmployeeEvent.newBuilder()
                .setEmployeeId(employee.getId().toString())
                .setName(employee.getName())
                .setEmail(employee.getEmail())
                .setEventType("EMPLOYEE_CREATED")
                .build();

        try {
            kafkaTemplate.send("employee", event.toByteArray());
        }
        catch (Exception e) {
            log.error("Error sending Employee_created event: {}", e);
        }
    }
}

