package com.repository.repository.config;

import dto.DepartmentDto;
import dto.RoomDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:${property-prefix}/kafka.properties")
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaConsumerConfig
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
    public ConsumerFactory<Long, Void> findAllConsumerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, findAllGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VoidDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, Void> findAllContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<Long, Void> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(findAllConsumerFactory());
        return factory;
    }

    //-------------------------------------------------------------

    @Bean
    public ConsumerFactory<Long, String> findByTemplateConsumerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, findByTemplateGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, String> findByTemplateContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(findByTemplateConsumerFactory());
        return factory;
    }

    //-------------------------------------------------------------

    @Bean
    public ConsumerFactory<Long, RoomDto> findByRoomConsumerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, findByRoomGroupId);
        return new DefaultKafkaConsumerFactory<>(config, new LongDeserializer(), new JsonDeserializer<>(RoomDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, RoomDto> findByRoomContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<Long, RoomDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(findByRoomConsumerFactory());
        return factory;
    }

    //-------------------------------------------------------------

    @Bean
    public ConsumerFactory<Long, DepartmentDto> findByDepartmentConsumerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, findByRoomGroupId);
        return new DefaultKafkaConsumerFactory<>(config, new LongDeserializer(), new JsonDeserializer<>(DepartmentDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, DepartmentDto> findByDepartmentContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<Long, DepartmentDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(findByDepartmentConsumerFactory());
        return factory;
    }

    //-------------------------------------------------------------
}


