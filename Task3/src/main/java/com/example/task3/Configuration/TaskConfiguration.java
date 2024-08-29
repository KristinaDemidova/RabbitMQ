package com.example.task3.Configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfiguration {
    public static final String QUEUE_NAME1 = "tasks_queue";
    public static final String QUEUE_NAME2 = "answers_queue2";
    @Bean
    public Queue queue1() {
        return new Queue(QUEUE_NAME1, true);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE_NAME2, true);
    }
}
