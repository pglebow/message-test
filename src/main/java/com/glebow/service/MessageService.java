/**
 * 
 */
package com.glebow.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.glebow.config.RabbitMQConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Philip Glebow
 *
 */
@Service
@Slf4j
@EnableScheduling
public class MessageService {

    @Autowired
    private RabbitTemplate template;

    private Integer number;

    /**
     * Default
     */
    public MessageService() {
        log.info("Starting MessageService");
        number = Integer.valueOf(0);
    }

    @RabbitListener(queues = "tasks")
    public void taskListener(String id) {
        try {
            Thread.sleep(2000);
            log.info("Received " + id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        if (message != null && !message.isEmpty()) {
            template.convertAndSend(RabbitMQConfig.TASK_QUEUE_NAME, message);
        }
    }

    @Scheduled(fixedDelay = 1000)
    protected void send() {
        number++;
        send(number.toString());
    }
}
