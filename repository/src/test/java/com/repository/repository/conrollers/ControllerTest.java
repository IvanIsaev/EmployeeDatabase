package com.repository.repository.conrollers;

import com.repository.repository.dto.EmployeeDto;
import com.repository.repository.entities.Department;
import com.repository.repository.entities.Employee;
import com.repository.repository.entities.Room;
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
        Map<Room, Integer> roomAndCountPeopleHere = new HashMap<>();

        roomAndCountPeopleHere.put(new Room(1, "101"), 5);
        roomAndCountPeopleHere.put(new Room(2, "102"), 3);
        roomAndCountPeopleHere.put(new Room(3, "103a"), 7);
        roomAndCountPeopleHere.put(new Room(4, "104a"), 2);

        roomAndCountPeopleHere.forEach((room, countPeople)->{assertThat(restController.findByRoom(room)).hasSize(countPeople);});
    }

    @Test
    void findEmployeesByDepartmentTest()
    {
        Department department = new Department();
        department.setId(1);

        List<EmployeeDto> employees = restController.findByDepartment(department);

        assertThat(employees).hasSize(5);
    }

}