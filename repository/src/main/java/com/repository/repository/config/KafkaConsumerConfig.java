package com.repository.repository.config;

import com.repository.repository.entities.Department;
import com.repository.repository.entities.Room;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.springframework.beans.factory.annotation.Value;
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
@PropertySource("classpath:kafka.properties")
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

//      ---------------------------------

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

//      ---------------------------------
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

//      ------------------------------------
    @Bean
    public ConsumerFactory<Long, Room> findByRoomConsumerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, findByRoomGroupId);
        return new DefaultKafkaConsumerFactory<>(config, new LongDeserializer(), new JsonDeserializer<>(Room.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, Room> findByRoomContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<Long, Room> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(findByRoomConsumerFactory());
        return factory;
    }
//      ----------------------------------

    @Bean
    public ConsumerFactory<Long, Department> findByDepartmentConsumerFactory()
    {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, findByRoomGroupId);
        return new DefaultKafkaConsumerFactory<>(config, new LongDeserializer(), new JsonDeserializer<>(Department.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, Department> findByDepartmentContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<Long, Department> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(findByDepartmentConsumerFactory());
        return factory;
    }


//      --------------------------------------
}

