/**
 * 
 */
package com.glebow.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glebow.config.RabbitMQConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Philip Glebow
 *
 */
@Service
@Slf4j
public class MessageService {

    @Autowired
    private RabbitTemplate template;

    /**
     * Default
     */
    public MessageService() {
        log.info("Starting MessageService");
    }

    @RabbitListener(queues = "tasks")
    public String taskListener(String id) {
        log.info(id);
        return id;
    }

    public void send(String message) {
        if (message != null && !message.isEmpty()) {
            template.convertAndSend(RabbitMQConfig.TASK_QUEUE_NAME, message);
        }
    }
}
