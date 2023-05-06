package com.example.task3.Controller;

import com.example.task3.Configuration.TaskConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
public class TaskController {
    private final RabbitTemplate rabbitTemplate;
    private final Random random;

    @Autowired
    TaskController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.random = new Random();
    }

    @PostMapping("/randomcreate")
    void randomcreate(Long number) {
        for (int i = 0; i < number;i++) {
            Long value = random.nextLong(1000);
            rabbitTemplate.convertAndSend(TaskConfiguration.QUEUE_NAME1, value);
        }
    }

    @PostMapping("/createTask")
    void createTask(Long number) {
        rabbitTemplate.convertAndSend(TaskConfiguration.QUEUE_NAME1, number);
    }
}
