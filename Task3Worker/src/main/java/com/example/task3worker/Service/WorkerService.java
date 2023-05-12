package com.example.task3worker.Service;

import com.example.task3worker.Configuration.WorkerConfiguration;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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
    private final Counter answers;
    private final Counter errors;
    private final RabbitTemplate rabbitTemplate;

    public WorkerService(RabbitTemplate rabbitTemplate, MeterRegistry meterRegistry) {
        this.rabbitTemplate = rabbitTemplate;
        answers = meterRegistry.counter("answers_worker_total");
        errors = meterRegistry.counter("errors_worker_total");
    }

    @RabbitListener(queues = {WorkerConfiguration.QUEUE_NAME1})
    public void work(Long number, @Header(AmqpHeaders.CONSUMER_QUEUE) String queueName, MessageHeaders headers) {
        answers.increment();
        log.info("Got new message: " + number + ", headers: " + headers.values() + "queueName -" + queueName);
        try {
            Thread.sleep(number);
            rabbitTemplate.convertAndSend(WorkerConfiguration.QUEUE_NAME2, number);
        } catch (Exception e) {
            errors.increment();
            log.info("Sleep Error");
        }
    }

    public static void workFromController(Long number) {
        log.info("Got new message from controller: " + number);
        try {
            Thread.sleep(number);
        } catch (Exception e) {
            log.info("Sleep Error");
        }
    }
}
