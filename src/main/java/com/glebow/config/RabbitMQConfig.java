/**
 * 
 */
package com.glebow.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.glebow.error.CustomErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Philip Glebow
 * @see https://spring.io/guides/gs/messaging-rabbitmq
 *
 */
@Configuration
@EnableRabbit
@Slf4j
public class RabbitMQConfig {
    
    public final static String TASK_QUEUE_NAME = "tasks";

    /**
     * Constructor
     */
    public RabbitMQConfig() {
        log.info("RabbitMQConfig starting");
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory cf = new CachingConnectionFactory();
        cf.setHost("localhost");
        return cf;
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory f = new SimpleRabbitListenerContainerFactory();
        f.setConnectionFactory(rabbitConnectionFactory());
        f.setConcurrentConsumers(3);
        f.setMaxConcurrentConsumers(10);
        f.setErrorHandler(new CustomErrorHandler());
        return f;
    }
    
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(rabbitConnectionFactory());
    }


    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory());
        template.setMandatory(true);
        template.setChannelTransacted(true);
        return template;
    }

    @Bean
    // Every queue is bound to the default direct exchange
    public Queue tasksQueue() {
        return new Queue(TASK_QUEUE_NAME);
    }
    
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }
    
    @Bean
    Binding binding() {
        return BindingBuilder.bind(tasksQueue()).to(exchange()).with(TASK_QUEUE_NAME);
    }
}
