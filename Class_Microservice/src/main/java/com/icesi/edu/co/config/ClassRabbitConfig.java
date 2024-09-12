package com.icesi.edu.co.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class ClassRabbitConfig {

    @Bean
    public Exchange classExchange() {
        return ExchangeBuilder.directExchange("class-exchange").durable(true).build();
    }

    @Bean
    public Queue classQueue() {
        return QueueBuilder.durable("class-queue")
                .withArgument("x-dead-letter-exchange", "class-dlx")
                .withArgument("x-dead-letter-routing-key", "class-dlq-routing-key")
                .build();
    }

    @Bean
    public Binding classQueueBinding() {
        return BindingBuilder.bind(classQueue()).to(classExchange()).with("class-routing-key").noargs();
    }

    @Bean
    public Exchange classDLXExchange() {
        return ExchangeBuilder.directExchange("class-dlx").durable(true).build();
    }

    @Bean
    public Queue classDLQ() {
        return QueueBuilder.durable("class-dlq").build();
    }

    @Bean
    public Binding classDLQBinding() {
        return BindingBuilder.bind(classDLQ()).to(classDLXExchange()).with("class-dlq-routing-key").noargs();
    }

    @Bean
    public FanoutExchange scheduleChangeExchange() {
        return new FanoutExchange("schedule-change-exchange");
    }

    @Bean
    public Queue scheduleChangeQueue() {
        return new Queue("schedule-change-queue");
    }

    @Bean
    public Queue scheduleChangeDeadLetterQueue() {
        return new Queue("schedule-change-dlq");
    }

    @Bean
    public Binding bindingScheduleChangeQueue(FanoutExchange scheduleChangeExchange, Queue scheduleChangeQueue) {
        return BindingBuilder.bind(scheduleChangeQueue).to(scheduleChangeExchange);
    }

    @Bean
    public Binding bindingScheduleChangeDLQ(FanoutExchange scheduleChangeExchange, Queue scheduleChangeDeadLetterQueue) {
        return BindingBuilder.bind(scheduleChangeDeadLetterQueue).to(scheduleChangeExchange);
    }
}
