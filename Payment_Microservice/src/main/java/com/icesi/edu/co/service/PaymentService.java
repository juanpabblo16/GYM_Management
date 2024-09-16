package com.icesi.edu.co.service;

import com.icesi.edu.co.model.Payment;
import com.icesi.edu.co.repository.PaymentRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private PaymentRepository paymentRepository;

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


    // Método para procesar pagos
    public Payment processPayment(Payment payment) {
        // Lógica para validar y procesar el pago
        return paymentRepository.save(payment);
    }

    // Otro método para obtener todos los pagos (opcional)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
