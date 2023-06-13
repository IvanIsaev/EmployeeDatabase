package com.repository.repository.conrollers;

import com.repository.repository.dto.EmployeeDto;
import com.repository.repository.entities.Department;
import com.repository.repository.entities.Employee;
import com.repository.repository.entities.Room;
import com.repository.repository.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class Controller
{
    CacheControllerLowerCase cacheControllerLowerCase;
    UserRepository userRepository;
    Logger logger;

    {
        cacheControllerLowerCase = new CacheControllerLowerCase();
        logger = Logger.getLogger(Controller.class.getName());
    }

    public Controller(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @GetMapping("/allEmployees")
    public List<EmployeeDto> getAllEmployees()
    {
        List<Employee> employees = userRepository.findAll();
        return employees.stream().map(employee -> new EmployeeDto(employee)).toList();
    }

    @GetMapping("/findByTemplate")
    public List<EmployeeDto> findByTemplate(@RequestParam String template)
    {
        List<EmployeeDto> employees;

        if(cacheControllerLowerCase.hasDataForQuery(template))
        {
            employees = cacheControllerLowerCase.extractDataForQuery(template);
            logger.info("Data from cache");
        }
        else
        {
            employees = userRepository.findByTemplateLike(template.toLowerCase())
                    .stream().map(employee -> new EmployeeDto(employee)).toList();
            cacheControllerLowerCase.refresh(template, employees);
        }

        return employees;
    }

    @GetMapping("/findByRoom")
    public List<EmployeeDto> findByRoom(@RequestBody Room room)
    {
        List<Employee> employees = userRepository.findByRoom(room);
        return employees.stream().map(employee -> new EmployeeDto(employee)).toList();
    }

    @GetMapping("/findByDepartment")
    public List<EmployeeDto> findByDepartment(@RequestBody Department department)
    {
        List<Employee> employees = userRepository.findByDepartment(department);
        return employees.stream().map(employee -> new EmployeeDto(employee)).toList();
    }
}
