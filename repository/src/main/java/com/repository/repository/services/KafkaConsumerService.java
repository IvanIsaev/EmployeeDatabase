package com.repository.repository.services;

import com.repository.repository.controllers.Controller;
import dto.DepartmentDto;
import dto.EmployeeDto;
import dto.RoomDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableKafka
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaConsumerService
{
    private Controller databaseRestController;
    private KafkaTemplate<String, List<EmployeeDto>> employeeKafkaTemplate;
    private Logger log;

    @Value("${answer.employees.topic.name}")
    private String answerTopicName;

    public KafkaConsumerService(Controller databaseRestController, KafkaTemplate<String, List<EmployeeDto>> employeeKafkaTemplate)
    {
        this.databaseRestController = databaseRestController;
        this.employeeKafkaTemplate = employeeKafkaTemplate;
        log = LogManager.getLogger(this.getClass());
    }

    @KafkaListener(topics = "${find.all.topic.name}", groupId = "${find.all.group-id}")
    public void findAllEmployeesListener(ConsumerRecord<String, Void> record)
    {
        List<EmployeeDto> all = databaseRestController.getAllEmployees();
        log.info("findAllEmployeesListener " + record.key());
        sendAnswer(record.key(), all);
    }

    @KafkaListener(topics = "${find.by.template.topic.name}", groupId = "${find.by.template.group-id}")
    public void findByTemplateListener(ConsumerRecord<String, String> record)
    {
        List<EmployeeDto> filtered = databaseRestController.findByTemplate(record.value());
        log.info("findByTemplateListener " + record.key());
        sendAnswer(record.key(), filtered);
    }

    @KafkaListener(topics = "${find.by.room.topic.name}", groupId = "${find.by.room.group-id}",
            containerFactory = "findByRoomContainerFactory")
    public void findByRoomListener(ConsumerRecord<String, RoomDto> record)
    {
        List<EmployeeDto> employeeByRoom = databaseRestController.findByRoomId(record.value().getId());
        log.info("findByRoomListener " + record.key());
        sendAnswer(record.key(), employeeByRoom);
    }

    @KafkaListener(topics = "${find.by.department.topic.name}", groupId = "${find.by.department.group-id}",
            containerFactory = "findByDepartmentContainerFactory")
    public void findByDepartment(ConsumerRecord<String, DepartmentDto> record)
    {
        List<EmployeeDto> employeeByDepartment = databaseRestController.findByDepartmentId(record.value().getId());
        log.info("findByDepartment " + record.key());
        sendAnswer(record.key(), employeeByDepartment);
    }

    private void sendAnswer(final String key, final List<EmployeeDto> answer)
    {
        employeeKafkaTemplate.send(answerTopicName, key, answer);
        employeeKafkaTemplate.flush();
    }

}
