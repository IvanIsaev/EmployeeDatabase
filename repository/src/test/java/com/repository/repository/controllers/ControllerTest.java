package com.repository.repository.controllers;

import dto.DepartmentDto;
import dto.EmployeeDto;
import dto.RoomDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ControllerTest
{
    @Autowired
    Controller restController;

    @Test
    void findAllTest()
    {
        assertThat(restController.getAllEmployees()).hasSize(17);
    }

    @Test
    void findEmployeesBySubstringTest()
    {
        final String template = "Але";

        List<EmployeeDto> employees_after_mask = restController.findByTemplate(template);

        assertThat(employees_after_mask).hasSize(4);
    }

    @Test
    void findEmployeesByRoomTest()
    {
        Map<RoomDto, Integer> roomAndCountPeopleHere = new HashMap<>();

        roomAndCountPeopleHere.put(new RoomDto(1), 5);
        roomAndCountPeopleHere.put(new RoomDto(2), 3);
        roomAndCountPeopleHere.put(new RoomDto(3), 7);
        roomAndCountPeopleHere.put(new RoomDto(4), 2);

        roomAndCountPeopleHere.forEach((room, countPeople)->{assertThat(restController.findByRoomId(room.getId())).hasSize(countPeople);});
    }

    @Test
    void findEmployeesByDepartmentTest()
    {
        DepartmentDto department = new DepartmentDto(1);

        List<EmployeeDto> employees = restController.findByDepartmentId(department.getId());

        assertThat(employees).hasSize(5);
    }

}