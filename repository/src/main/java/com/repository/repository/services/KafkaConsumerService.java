package com.repository.repository.services;

import com.repository.repository.conrollers.Controller;
import com.repository.repository.dto.EmployeeDto;
import com.repository.repository.entities.Department;
import com.repository.repository.entities.Room;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableKafka
public class KafkaConsumerService
{
    private Controller databaseRestController;
    private KafkaTemplate<Long, List<EmployeeDto> > employeeKafkaTemplate;

    @Value("${answer.employees.topic.name}")
    private String answerTopicName;

    public KafkaConsumerService(Controller databaseRestController, KafkaTemplate<Long, List<EmployeeDto> > employeeKafkaTemplate)
    {
        this.databaseRestController = databaseRestController;
        this.employeeKafkaTemplate = employeeKafkaTemplate;
    }

    @KafkaListener(topics = "${find.all.topic.name}", groupId = "${find.all.group-id}")
    public void findAllEmployeesListener(ConsumerRecord<Long, Void> record)
    {
        List<EmployeeDto> all = databaseRestController.getAllEmployees();
        sendAnswer(all);
    }

    @KafkaListener(topics = "${find.by.template.topic.name}", groupId = "${find.by.template.group-id}")
    public void findByTemplateListener(ConsumerRecord<Long, String> record)
    {
        List<EmployeeDto> filtered = databaseRestController.findByTemplate(record.value());
        sendAnswer(filtered);
    }

    @KafkaListener(topics = "${find.by.room.topic.name}", groupId = "${find.by.room.group-id}",
            containerFactory = "findByRoomContainerFactory")
    public void findByRoomListener(ConsumerRecord<Long, Room> record)
    {
        List<EmployeeDto> employeeByRoom = databaseRestController.findByRoom(record.value());
        sendAnswer(employeeByRoom);
    }

    @KafkaListener(topics = "${find.by.department.topic.name}", groupId = "${find.by.department.group-id}",
            containerFactory = "findByDepartmentContainerFactory")
    public void findByDepartment(ConsumerRecord<Long, Department> record)
    {
        List<EmployeeDto> employeeByDepartment = databaseRestController.findByDepartment(record.value());
        sendAnswer(employeeByDepartment);
    }

    private void sendAnswer(final List<EmployeeDto> answer)
    {
        employeeKafkaTemplate.send(answerTopicName, answer);
        employeeKafkaTemplate.flush();
    }

}
