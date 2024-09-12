package com.icesi.edu.co.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentConfig {

    @Bean
    public Queue pagosQueue() {
        return new Queue("pagos-queue", true, false, true, Map.of(
                "x-dead-letter-exchange", "dlx-exchange"
        ));
    }

    @Bean
    public Queue pagosDlq() {
        return new Queue("pagos-dlq", true);
    }

    @Bean
    public FanoutExchange dlxExchange() {
        return new FanoutExchange("dlx-exchange");
    }

    @Bean
    public Binding bindingPagosDlq(FanoutExchange dlxExchange, Queue pagosDlq) {
        return BindingBuilder.bind(pagosDlq).to(dlxExchange);
    }

    @Bean
    public Binding bindingPagosQueue(FanoutExchange dlxExchange, Queue pagosQueue) {
        return BindingBuilder.bind(pagosQueue).to(dlxExchange);
    }
}
