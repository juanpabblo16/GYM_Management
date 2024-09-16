package com.icesi.edu.co.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRabbitConfig {

    public static final String PAYMENTS_QUEUE = "payments-queue";
    public static final String PAYMENTS_DLQ = "payments-dlq";

    @Bean
    public Queue paymentsQueue() {
        return QueueBuilder.durable(PAYMENTS_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", PAYMENTS_DLQ)
                .build();
    }

    @Bean
    public Queue paymentsDeadLetterQueue() {
        return QueueBuilder.durable(PAYMENTS_DLQ).build();
    }

    @Bean
    public DirectExchange paymentsExchange() {
        return new DirectExchange("payments-exchange");
    }

    @Bean
    public Binding paymentsBinding(Queue paymentsQueue, DirectExchange paymentsExchange) {
        return BindingBuilder.bind(paymentsQueue).to(paymentsExchange).with("payment.routing.key");
    }
}
