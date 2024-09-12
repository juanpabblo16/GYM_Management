package com.icesi.edu.co.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class PaymentRabbitConfig {

    @Bean
    public Exchange paymentExchange() {
        return ExchangeBuilder.directExchange("payment-exchange").durable(true).build();
    }

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable("payment-queue")
                .withArgument("x-dead-letter-exchange", "payment-dlx")
                .withArgument("x-dead-letter-routing-key", "payment-dlq-routing-key")
                .build();
    }

    @Bean
    public Binding paymentQueueBinding() {
        return BindingBuilder.bind(paymentQueue()).to(paymentExchange()).with("payment-routing-key").noargs();
    }

    @Bean
    public Exchange paymentDLXExchange() {
        return ExchangeBuilder.directExchange("payment-dlx").durable(true).build();
    }

    @Bean
    public Queue paymentDLQ() {
        return QueueBuilder.durable("payment-dlq").build();
    }

    @Bean
    public Binding paymentDLQBinding() {
        return BindingBuilder.bind(paymentDLQ()).to(paymentDLXExchange()).with("payment-dlq-routing-key").noargs();
    }
}
