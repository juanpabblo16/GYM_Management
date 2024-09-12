package com.icesi.edu.co.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class TrainerRabbitConfig {

    @Bean
    public Exchange trainerExchange() {
        return ExchangeBuilder.directExchange("trainer-exchange").durable(true).build();
    }

    @Bean
    public Queue trainerQueue() {
        return QueueBuilder.durable("trainer-queue")
                .withArgument("x-dead-letter-exchange", "trainer-dlx")
                .withArgument("x-dead-letter-routing-key", "trainer-dlq-routing-key")
                .build();
    }

    @Bean
    public Binding trainerQueueBinding() {
        return BindingBuilder.bind(trainerQueue()).to(trainerExchange()).with("trainer-routing-key").noargs();
    }

    @Bean
    public Exchange trainerDLXExchange() {
        return ExchangeBuilder.directExchange("trainer-dlx").durable(true).build();
    }

    @Bean
    public Queue trainerDLQ() {
        return QueueBuilder.durable("trainer-dlq").build();
    }

    @Bean
    public Binding trainerDLQBinding() {
        return BindingBuilder.bind(trainerDLQ()).to(trainerDLXExchange()).with("trainer-dlq-routing-key").noargs();
    }
}
