package com.gate.gate.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EmployeeDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySource("classpath:${property-prefix}/kafka.properties")
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaConsumerConfig
{
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${answer.employees.group-id}")
    private String employeesGroupId;

    @Bean
    public ConsumerFactory<Long, List<EmployeeDto>> consumerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, employeesGroupId);

        return new DefaultKafkaConsumerFactory<>(config, new LongDeserializer(), valueDeserializer());
    }

    @Bean(name = "containerFactoryEmployee")
    public ConcurrentKafkaListenerContainerFactory<Long, List<EmployeeDto>> containerFactoryEmployee()
    {
        ConcurrentKafkaListenerContainerFactory<Long, List<EmployeeDto>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }


    protected Deserializer<List<EmployeeDto>> valueDeserializer() {
        ObjectMapper om = new ObjectMapper();
        JavaType type = om.getTypeFactory().constructParametricType(List.class, EmployeeDto.class);
        return new JsonDeserializer<List<EmployeeDto>>(type, om, false);
    }
}
