/**
 * 
 */
package com.glebow.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    private int count;

    /**
     * Default
     */
    public MessageService() {
        log.info("Starting MessageService");
        number = Integer.valueOf(0);
        count = 0;
    }

    @RabbitListener(queues = "tasks")
    @Transactional(readOnly=false, rollbackFor=IllegalStateException.class)
    public void taskListener(String id) {
        if ( ++count % 5 == 0 ) {
            throw new IllegalStateException("Rolling back " + id);
        } else {
            log.info("Consuming " + id);            
        }
    }

    public void send(String message) {
        if (message != null && !message.isEmpty()) {
            Message m = MessageBuilder.withBody(message.getBytes()).setHeader(AmqpHeaders.DELIVERY_MODE, MessageDeliveryMode.PERSISTENT).build();
            log.info("Sending " + message);
            template.convertAndSend(RabbitMQConfig.TASK_QUEUE_NAME, m);
        }
    }

    @Scheduled(fixedDelay = 1000)
    protected void send() {
        number++;
        send(number.toString());
    }
}
