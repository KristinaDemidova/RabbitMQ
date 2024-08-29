package com.example.task3.Service;

import com.example.task3.Configuration.TaskConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskSender {
    @RabbitListener(queues = TaskConfiguration.QUEUE_NAME2)
    public void getAnswer(Long result, @Header(AmqpHeaders.CONSUMER_QUEUE) String queueName,
                          MessageHeaders headers) {
        log.info("Successfully done sleep for " + result + ", headers: " + headers.values() + " queues" + queueName);
    }
}
