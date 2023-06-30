package com.gate.gate.controllers;

import com.gate.gate.services.KafkaListener;
import dto.DepartmentDto;
import dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class Gate
{
    @Autowired
    private KafkaTemplate<Long, Void > findAll;
    @Value("${find.all.topic.name}")
    private String findAllTopicName;

    @Autowired
    private KafkaTemplate<Long, String > findByTemplate;
    @Value("${find.by.template.topic.name}")
    private String findByTemplateTopicName;

    @Autowired
    private KafkaTemplate<Long, RoomDto > findByRoom;
    @Value("${find.by.room.topic.name}")
    private String findByRoomTopicName;

    @Autowired
    private KafkaTemplate<Long, DepartmentDto > findByDepartment;
    @Value("${find.by.department.topic.name}")
    private String findByDepartmentTopicName;

    @Autowired
    private KafkaListener listener;

    @GetMapping("/allEmployees")
    public void findAll()
    {
        findAll.send(findAllTopicName, null);
        findAll.flush();

        listener.receiveAll(Duration.ofSeconds(10000)).stream().forEach(employeeDto ->
                System.out.println(employeeDto.getName() + " " + employeeDto.getLastName()));
    }

    @GetMapping("/findByTemplate")
    public void findByTemplate(@RequestParam String template)
    {
        findByTemplate.send(findByTemplateTopicName, template);
        findByTemplate.flush();

        listener.receiveAll(Duration.ofSeconds(10000)).stream().forEach(employeeDto ->
                System.out.println(employeeDto.getName() + " " + employeeDto.getLastName()));
    }

    @GetMapping("/findByRoom")
    public void findByRoom(@RequestBody RoomDto room)
    {
        findByRoom.send(findByRoomTopicName, room);
        findByRoom.flush();

        listener.receiveAll(Duration.ofSeconds(10000)).stream().forEach(employeeDto ->
                System.out.println(employeeDto.getName() + " " + employeeDto.getLastName()));
    }

    @GetMapping("/findByDepartment")
    public void findByDepartment(@RequestBody DepartmentDto department)
    {
        findByDepartment.send(findByDepartmentTopicName, department);
        findByDepartment.flush();

        listener.receiveAll(Duration.ofSeconds(10000)).stream().forEach(employeeDto ->
                System.out.println(employeeDto.getName() + " " + employeeDto.getLastName()));
    }
}
