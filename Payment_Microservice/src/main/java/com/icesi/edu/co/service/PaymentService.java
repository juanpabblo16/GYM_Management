package com.icesi.edu.co.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitListener(queues = "payment-queue")
    public void handlePayment(String paymentMessage) {
        try {
            // Aquí procesas el pago
            System.out.println("Processing payment: " + paymentMessage);
            // Simulando un error en el procesamiento
            throw new RuntimeException("Payment processing error");
        } catch (Exception e) {
            // Enviar el mensaje a la DLQ si hay un error
            amqpTemplate.convertAndSend("payment-dlx", "payment-dlq-routing-key", paymentMessage);
        }
    }
}
