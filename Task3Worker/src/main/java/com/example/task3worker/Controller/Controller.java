package com.example.task3worker.Controller;

import com.example.task3worker.Service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
@RestController
@Slf4j
public class Controller {
    private final Random random;

    @Autowired
    Controller() {
        this.random = new Random();
    }

    @PostMapping("/randomcreatebyworker")
    void randomcreate(Long number) {
        for (int i = 0; i < number;i++) {
            Long value = random.nextLong(1000);
            WorkerService.workFromController(value);
        }
    }

    @PostMapping("/createTaskbyworker")
    void createTask(Long number) {
        WorkerService.workFromController(number);
    }
}
