package com.icesi.edu.co.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class MemberRabbitConfig {

    @Bean
    public Exchange notificationExchange() {
        return ExchangeBuilder.topicExchange("notification-exchange").durable(true).build();
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable("notification-queue").build();
    }

    @Bean
    public Binding notificationQueueBinding() {
        return BindingBuilder.bind(notificationQueue()).to(notificationExchange()).with("notification.routing.key").noargs();
    }

    @Bean
    public Exchange memberExchange() {
        return ExchangeBuilder.directExchange("member-exchange").durable(true).build();
    }

    @Bean
    public Queue memberQueue() {
        return QueueBuilder.durable("member-queue")
                .withArgument("x-dead-letter-exchange", "member-dlx")
                .withArgument("x-dead-letter-routing-key", "member-dlq-routing-key")
                .build();
    }

    @Bean
    public Binding memberQueueBinding() {
        return BindingBuilder.bind(memberQueue()).to(memberExchange()).with("member-routing-key").noargs();
    }

    @Bean
    public Exchange memberDLXExchange() {
        return ExchangeBuilder.directExchange("member-dlx").durable(true).build();
    }

    @Bean
    public Queue memberDLQ() {
        return QueueBuilder.durable("member-dlq").build();
    }

    @Bean
    public Binding memberDLQBinding() {
        return BindingBuilder.bind(memberDLQ()).to(memberDLXExchange()).with("member-dlq-routing-key").noargs();
    }
}
