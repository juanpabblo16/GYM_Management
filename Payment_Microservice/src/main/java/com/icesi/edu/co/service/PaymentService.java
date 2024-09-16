package com.icesi.edu.co.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icesi.edu.co.model.Payment;

@Service
public class PaymentService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitListener(queues = "payment-queue")
    public void handlePayment(Payment payment) {
        try {
            System.out.println("Processing payment: " + payment);
            throw new RuntimeException("Payment processing error");
        } catch (Exception e) {
            amqpTemplate.convertAndSend("payment-dlx", "payment-dlq-routing-key", payment);
        }
    }
}
