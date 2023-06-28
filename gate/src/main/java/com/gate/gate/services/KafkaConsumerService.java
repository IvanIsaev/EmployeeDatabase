package com.gate.gate.services;

import dto.EmployeeDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@EnableKafka
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaConsumerService
{
    @KafkaListener(topics = "${answer.employees.topic.name}", groupId = "${answer.employees.group-id}",
            containerFactory = "containerFactoryEmployee")
    public void employeeListener(ConsumerRecord<Long, List<EmployeeDto>> record)
    {
        System.out.println(record.key());
        System.out.println(record.value().get(0).getName());
    }

}
