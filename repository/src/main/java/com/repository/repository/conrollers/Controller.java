package com.repository.repository.conrollers;

import com.repository.repository.entities.Employee;
import com.repository.repository.entities.Post;
import com.repository.repository.repositories.UserRepository;
import dto.EmployeeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
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

    public List<EmployeeDto> getAllEmployees()
    {
        List<Employee> employees = userRepository.findAll();
        return employees.stream().map(employee -> employeeDtoFrom(employee)).toList();
    }

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
                    .stream().map(employee -> employeeDtoFrom(employee)).toList();
            cacheControllerLowerCase.refresh(template, employees);
        }

        return employees;
    }

    public List<EmployeeDto> findByRoomId(int roomId)
    {
        List<Employee> employees = userRepository.findByRoomId(roomId);
        return employees.stream().map(employee -> employeeDtoFrom(employee)).toList();
    }

    public List<EmployeeDto> findByDepartmentId(int departmentId)
    {
        List<Employee> employees = userRepository.findByDepartmentId(departmentId);
        return employees.stream().map(employee -> employeeDtoFrom(employee)).toList();
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
