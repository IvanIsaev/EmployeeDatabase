package com.gate.gate.config;

import dto.DepartmentDto;
import dto.EmployeeDto;
import dto.RoomDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:${property-prefix}/kafka.properties")
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaProducerConfig
{
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${find.all.group-id}")
    private String findAllGroupId;

    @Value("${find.by.template.group-id}")
    private String findByTemplateGroupId;

    @Value("${find.by.room.group-id}")
    private String findByRoomGroupId;

    @Value("${find.by.department.group-id}")
    private String findByDepartmentGroupId;

    //-------------------------------------------------------------

    @Bean
    public ProducerFactory<Long, Void> findAllProducerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VoidSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "findAll")
    public KafkaTemplate<Long, Void > kafkaTemplateFindAll()
    {
        return new KafkaTemplate<>(findAllProducerFactory());
    }

    //-------------------------------------------------------------

    @Bean
    public ProducerFactory<Long, String> findByTemplateProducerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "findByTemplate")
    public KafkaTemplate<Long, String > kafkaTemplateFindByTemplate()
    {
        return new KafkaTemplate<>(findByTemplateProducerFactory());
    }

    //-------------------------------------------------------------

    @Bean
    public ProducerFactory<Long, RoomDto> findByRoomProducerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        return new DefaultKafkaProducerFactory<>(config, new LongSerializer(), new JsonSerializer<RoomDto>());
    }

    @Bean(name = "findByRoom")
    public KafkaTemplate<Long, RoomDto > kafkaTemplateFindByRoom()
    {
        return new KafkaTemplate<>(findByRoomProducerFactory());
    }

    //-------------------------------------------------------------

    @Bean
    public ProducerFactory<Long, DepartmentDto> findByDepartmentProducerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        return new DefaultKafkaProducerFactory<>(config, new LongSerializer(), new JsonSerializer<DepartmentDto>());
    }

    @Bean(name = "findByDepartment")
    public KafkaTemplate<Long, DepartmentDto > kafkaTemplateFindByDepartment()
    {
        return new KafkaTemplate<>(findByDepartmentProducerFactory());
    }

    //-------------------------------------------------------------
}


