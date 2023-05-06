package com.example.task3worker.Service;

import com.example.task3worker.Configuration.WorkerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkerService {
    private final RabbitTemplate rabbitTemplate;

    public WorkerService (RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = {WorkerConfiguration.QUEUE_NAME1})
    public void work(Long number, @Header(AmqpHeaders.CONSUMER_QUEUE) String queueName, MessageHeaders headers) {
        log.info("Got new message: " + number + ", headers: " + headers.values() + "queueName -" + queueName);
        try {
            Thread.sleep(number);
            rabbitTemplate.convertAndSend(WorkerConfiguration.QUEUE_NAME2, number);
        } catch (Exception e) {
            log.info("Sleep Error");
        }
    }
}
