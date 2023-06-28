package com.gate.gate.controllers;

import dto.DepartmentDto;
import dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/allEmployees")
    public void findAll()
    {
        System.out.println("allEmployees");
        findAll.send(findAllTopicName, null);
        findAll.flush();
    }

    @GetMapping("/findByTemplate")
    public void findByTemplate(@RequestParam String template)
    {
        System.out.println(template);
        findByTemplate.send(findByTemplateTopicName, template);
        findByTemplate.flush();
    }

    @GetMapping("/findByRoom")
    public void findByRoom(@RequestBody RoomDto room)
    {
        System.out.println(room);
        findByRoom.send(findByRoomTopicName, room);
        findByRoom.flush();
    }

    @GetMapping("/findByDepartment")
    public void findByDepartment(@RequestBody DepartmentDto department)
    {
        System.out.println(department);
        findByDepartment.send(findByDepartmentTopicName, department);
        findByDepartment.flush();
    }
}
