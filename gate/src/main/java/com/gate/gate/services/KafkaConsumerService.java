//package com.gate.gate.services;
//
//import dto.EmployeeDto;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.OffsetAndMetadata;
//import org.apache.kafka.common.TopicPartition;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.*;
//
//@Service
//public class KafkaListenerService
//{
//
//    Consumer<Long, List<EmployeeDto>> consumer;
//
//    private final String topicName;
//
//    public KafkaListenerService(ConcurrentKafkaListenerContainerFactory<Long, List<EmployeeDto>> containerFactory,
//                                @Value("${answer.employees.topic.name}") String consumerTopicName)
//    {
//        topicName = consumerTopicName;
//        consumer = (Consumer<Long, List<EmployeeDto>>) containerFactory.getConsumerFactory().createConsumer();
//        consumer.subscribe(Arrays.asList(topicName));
//    }
//
//    public List<EmployeeDto> receiveAll(Duration await)
//    {
//        final ConsumerRecords<Long, List<EmployeeDto>> records = consumer.poll(await);
//
//        Iterator<ConsumerRecord<Long, List<EmployeeDto>>> iter = records.iterator();
//
//        List<EmployeeDto> answer = new LinkedList<>();
//
//        while(iter.hasNext())
//            answer.addAll(iter.next().value());
//
//        Map<TopicPartition, OffsetAndMetadata> commitedRes = consumer.committed(new HashSet<>(Arrays.asList(new TopicPartition(topicName, 0))));
//
//        commitedRes.forEach((part, offset) -> {
//            System.out.println(part.topic() + " " + offset.offset());
//        });
//
//        consumer.commitAsync();
//
////        commitedRes = consumer.committed(new HashSet<>(Arrays.asList(new TopicPartition(topicName, 0))));
////        commitedRes.forEach((part, offset) -> {
////            System.out.println(part.topic() + " " + offset.offset());
////        });
//
//        return answer;
//    }
//
//}

package com.gate.gate.services;

import com.gate.gate.exceptions.NoDataException;
import dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

@Service
@EnableKafka
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaConsumerService
{
    private ConcurrentMap<String, AnswerData> threadData;

    {
        threadData = new ConcurrentHashMap<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class AnswerData
    {
        private List<EmployeeDto> answer;
        private CountDownLatch countDownLatch;
    }

    @KafkaListener(topics = "${answer.employees.topic.name}", groupId = "${answer.employees.group-id}",
            containerFactory = "containerFactoryEmployee")
    public void employeeListener(ConsumerRecord<String, List<EmployeeDto>> record)
    {
        if(!threadData.containsKey(record.key())) return;

        threadData.get(record.key()).setAnswer(record.value());
        threadData.get(record.key()).getCountDownLatch().countDown();
    }

    public CountDownLatch getCountDownLatch(final String threadName)
    {
        AnswerData old = threadData.put(threadName, new AnswerData(new ArrayList<>(), new CountDownLatch(1)));
        WeakReference<AnswerData> wr = new WeakReference<>(old);
        return threadData.get(threadName).getCountDownLatch();
    }

    public List<EmployeeDto> getListData(final String threadName) throws NoDataException
    {
        if(!threadData.containsKey(threadName))
            throw new NoDataException(String.format("Thread with name %s not registered for await data", threadName));

        return threadData.get(threadName).getAnswer();
    }

}
