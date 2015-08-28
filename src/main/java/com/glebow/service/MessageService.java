/**
 * 
 */
package com.glebow.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Philip Glebow
 *
 */
@Service
@Slf4j
public class MessageService {

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
}
