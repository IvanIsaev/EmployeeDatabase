package com.repository.repository.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:${property-prefix}/kafka.properties")
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaTopicConfig
{
    @Value(value = "${find.all.topic.name}")
    private String allEmployees;

    @Value(value = "${find.by.template.topic.name}")
    private String findByTemplate;

    @Value(value = "${find.by.room.topic.name}")
    private String findByRoom;

    @Value(value = "${find.by.department.topic.name}")
    private String findByDepartment;

    @Value(value = "${answer.employees.topic.name}")
    private String foundedEmployees;

    @Bean
    public NewTopic allEmployeeTopic()
    {
        return new NewTopic(allEmployees, 1, (short) 1);
    }

    @Bean
    public NewTopic findByTemplateTopic()
    {
        return new NewTopic(findByTemplate, 1, (short) 1);
    }

    @Bean
    public NewTopic findByRoomTopic()
    {
        return new NewTopic(findByRoom, 1, (short) 1);
    }

    @Bean
    public NewTopic findByDepartmentTopic()
    {
        return new NewTopic(findByDepartment, 1, (short) 1);
    }

    @Bean
    public NewTopic foundedEmployeesTopic()
    {
        return new NewTopic(foundedEmployees, 1, (short) 1);
    }

}
