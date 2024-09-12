package com.icesi.edu.co.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class TeamRabbitConfig {

    @Bean
    public Exchange teamExchange() {
        return ExchangeBuilder.directExchange("team-exchange").durable(true).build();
    }

    @Bean
    public Queue teamQueue() {
        return QueueBuilder.durable("team-queue")
                .withArgument("x-dead-letter-exchange", "team-dlx")
                .withArgument("x-dead-letter-routing-key", "team-dlq-routing-key")
                .build();
    }

    @Bean
    public Binding teamQueueBinding() {
        return BindingBuilder.bind(teamQueue()).to(teamExchange()).with("team-routing-key").noargs();
    }

    @Bean
    public Exchange teamDLXExchange() {
        return ExchangeBuilder.directExchange("team-dlx").durable(true).build();
    }

    @Bean
    public Queue teamDLQ() {
        return QueueBuilder.durable("team-dlq").build();
    }

    @Bean
    public Binding teamDLQBinding() {
        return BindingBuilder.bind(teamDLQ()).to(teamDLXExchange()).with("team-dlq-routing-key").noargs();
    }
}
