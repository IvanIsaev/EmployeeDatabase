package com.repository.repository.controllers;

import com.repository.repository.entities.Employee;
import com.repository.repository.entities.Post;
import com.repository.repository.repositories.UserRepository;
import dto.EmployeeDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Controller
{
    UserRepository userRepository;
    Logger log;

    public Controller(UserRepository userRepository)
    {
        log = LogManager.getLogger(Controller.class.getName());
        this.userRepository = userRepository;
    }

    public List<EmployeeDto> getAllEmployees()
    {
        final List<Employee> employees = userRepository.findAll();

        log.info(String.format("Function name = %s, employee count = %d", "getAllEmployees", employees.size()));

        final List<EmployeeDto> foundedEmployees = employees.stream().map(employee -> employeeDtoFrom(employee)).toList();
        foundedEmployees.forEach(employeeDto -> log.info(employeeDto.toString()));

        return foundedEmployees;
    }

    public List<EmployeeDto> findByTemplate(@RequestParam String template)
    {
        final List<Employee> employees = userRepository.findByTemplateLike(template.toLowerCase());

        log.info(String.format("Function name = %s, employee count = %d, search param = %s",
                "findByTemplate", employees.size(), template));

        final List<EmployeeDto> foundedEmployees = employees.stream().map(employee -> employeeDtoFrom(employee)).toList();
        foundedEmployees.forEach(employeeDto -> log.info(employeeDto.toString()));

        return foundedEmployees;
    }

    public List<EmployeeDto> findByRoomId(int roomId)
    {
        List<Employee> employees = userRepository.findByRoomId(roomId);

        log.info(String.format("Function name = %s, employee count = %d, search param = %d",
                "findByRoomId", employees.size(), roomId));

        final List<EmployeeDto> foundedEmployees = employees.stream().map(employee -> employeeDtoFrom(employee)).toList();
        foundedEmployees.forEach(employeeDto -> log.info(employeeDto.toString()));

        return foundedEmployees;
    }

    public List<EmployeeDto> findByDepartmentId(int departmentId)
    {
        List<Employee> employees = userRepository.findByDepartmentId(departmentId);

        log.info(String.format("Function name = %s, employee count = %d, search param = %d",
                "findByDepartmentId", employees.size(), departmentId));

        final List<EmployeeDto> foundedEmployees = employees.stream().map(employee -> employeeDtoFrom(employee)).toList();
        foundedEmployees.forEach(employeeDto -> log.info(employeeDto.toString()));

        return foundedEmployees;
    }

    private EmployeeDto employeeDtoFrom(final Employee employee)
    {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setName(employee.getName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setPatronymic(employee.getPatronymic());
        employeeDto.setBirthday(employee.getBirthday());
        employeeDto.setIconName(employee.getIconName());
        employeeDto.setPosts(employee.getPosts().stream().map(Post::getName).collect(Collectors.toSet()));
        employeeDto.setRoom(employee.getRoom().toString());
        employeeDto.setDepartment(employee.getDepartment().toString());
        Optional.ofNullable(employee.getPhoneNumber()).ifPresent(pn -> employeeDto.setPhoneNumber(pn.toString()));

        return employeeDto;
    }
}
