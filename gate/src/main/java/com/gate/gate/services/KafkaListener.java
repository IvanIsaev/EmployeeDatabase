package com.gate.gate.services;

import dto.EmployeeDto;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class KafkaListener
{

    Consumer<Long, List<EmployeeDto>> consumer;

    public KafkaListener(ConcurrentKafkaListenerContainerFactory<Long, List<EmployeeDto>> containerFactory,
                         @Value("${answer.employees.topic.name}") String consumerTopicName)
    {
        consumer = (Consumer<Long, List<EmployeeDto>>) containerFactory.getConsumerFactory().createConsumer();
        consumer.subscribe(Arrays.asList(consumerTopicName));
    }

    public List<EmployeeDto> receiveAll(Duration await)
    {
        final ConsumerRecords<Long, List<EmployeeDto>> records = consumer.poll(await);

        Iterator<ConsumerRecord<Long, List<EmployeeDto>>> iter = records.iterator();

        List<EmployeeDto> answer = new LinkedList<>();

        while(iter.hasNext())
            answer.addAll(iter.next().value());

        return answer;
    }

}
