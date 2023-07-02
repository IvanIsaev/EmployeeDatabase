package com.gate.gate.controllers;

import com.gate.gate.exceptions.NoDataException;
import com.gate.gate.services.KafkaConsumerService;
import dto.DepartmentDto;
import dto.EmployeeDto;
import dto.RoomDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class Gate
{
    @Autowired
    private KafkaTemplate<String, Void > findAll;
    @Value("${find.all.topic.name}")
    private String findAllTopicName;

    @Autowired
    private KafkaTemplate<String, String > findByTemplate;
    @Value("${find.by.template.topic.name}")
    private String findByTemplateTopicName;

    @Autowired
    private KafkaTemplate<String, RoomDto > findByRoom;
    @Value("${find.by.room.topic.name}")
    private String findByRoomTopicName;

    @Autowired
    private KafkaTemplate<String, DepartmentDto > findByDepartment;
    @Value("${find.by.department.topic.name}")
    private String findByDepartmentTopicName;

    private KafkaConsumerService listener;

    CacheLowerCase cache;

    Logger log;

    public Gate(KafkaConsumerService listener)
    {
        this.listener = listener;
        cache = new CacheLowerCase();
        log = LogManager.getLogger(this.getClass());
    }

    @GetMapping("/allEmployees")
    public void findAllEmployees()
    {
        final String threadName = Thread.currentThread().getName();

        findAll.send(findAllTopicName, threadName, null);
        findAll.flush();

        List<EmployeeDto> answer = null;
        try
        {
            answer = awaitData(threadName);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
            // Страничка с ошибкой
        } catch (NoDataException e)
        {
            throw new RuntimeException(e);
            // Страничка с ошибкой
        }

        printLog("findAllEmployees", answer);
    }

    @GetMapping("/findByTemplate")
    public void findByTemplate(@RequestParam String template)
    {
        final String templateLowerCase = template.toLowerCase();

        List<EmployeeDto> answer = null;

        if(cache.hasDataForQuery(templateLowerCase))
        {
            answer = cache.extractDataForQuery(templateLowerCase);
            log.info("Data from cache");
        }
        else
        {
            final String threadName = Thread.currentThread().getName();
            findByTemplate.send(findByTemplateTopicName, threadName, template);
            findByTemplate.flush();

            try
            {
                answer = awaitData(threadName);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
                // Страничка с ошибкой
            } catch (NoDataException e)
            {
                throw new RuntimeException(e);
                // Страничка с ошибкой
            }

            WeakReference<CacheLowerCase> wr = new WeakReference<>(cache);
            cache = new CacheLowerCase(templateLowerCase, answer);
        }

        printLog("findByTemplate", answer);
    }

    @GetMapping("/findByRoom")
    public void findByRoom(@RequestBody RoomDto room)
    {
        final String threadName = Thread.currentThread().getName();

        findByRoom.send(findByRoomTopicName, threadName, room);
        findByRoom.flush();

        List<EmployeeDto> answer = null;
        try
        {
            answer = awaitData(threadName);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
            // Страничка с ошибкой
        } catch (NoDataException e)
        {
            throw new RuntimeException(e);
            // Страничка с ошибкой
        }

        printLog("findByRoom", answer);
    }

    @GetMapping("/findByDepartment")
    public void findByDepartment(@RequestBody DepartmentDto department)
    {
        final String threadName = Thread.currentThread().getName();

        findByDepartment.send(findByDepartmentTopicName, threadName, department);
        findByDepartment.flush();

        List<EmployeeDto> answer = null;
        try
        {
            answer = awaitData(threadName);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
            // Страничка с ошибкой
        } catch (NoDataException e)
        {
            throw new RuntimeException(e);
            // Страничка с ошибкой
        }

        printLog("findByDepartment", answer);
    }

    private final List<EmployeeDto> awaitData(final String threadName) throws InterruptedException, NoDataException
    {
        listener.getCountDownLatch(threadName).await(10, TimeUnit.SECONDS);
        return listener.getListData(threadName);
    }

    private void printLog(final String message, final List<EmployeeDto> employees)
    {
        log.info(message);
        employees.forEach(employeeDto -> log.info(employeeDto.toString()));
    }
}
