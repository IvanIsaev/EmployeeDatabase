package com.gate.gate.config;

import dto.DepartmentDto;
import dto.RoomDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.*;
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
    public ProducerFactory<String, Void> findAllProducerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VoidSerializer.class);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "findAll")
    public KafkaTemplate<String, Void > kafkaTemplateFindAll()
    {
        return new KafkaTemplate<>(findAllProducerFactory());
    }

    //-------------------------------------------------------------

    @Bean
    public ProducerFactory<String, String> findByTemplateProducerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean(name = "findByTemplate")
    public KafkaTemplate<String, String > kafkaTemplateFindByTemplate()
    {
        return new KafkaTemplate<>(findByTemplateProducerFactory());
    }

    //-------------------------------------------------------------

    @Bean
    public ProducerFactory<String, RoomDto> findByRoomProducerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer<RoomDto>());
    }

    @Bean(name = "findByRoom")
    public KafkaTemplate<String, RoomDto > kafkaTemplateFindByRoom()
    {
        return new KafkaTemplate<>(findByRoomProducerFactory());
    }

    //-------------------------------------------------------------

    @Bean
    public ProducerFactory<String, DepartmentDto> findByDepartmentProducerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer<DepartmentDto>());
    }

    @Bean(name = "findByDepartment")
    public KafkaTemplate<String, DepartmentDto > kafkaTemplateFindByDepartment()
    {
        return new KafkaTemplate<>(findByDepartmentProducerFactory());
    }

    //-------------------------------------------------------------
}


