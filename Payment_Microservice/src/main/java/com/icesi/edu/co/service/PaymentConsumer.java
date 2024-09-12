package com.icesi.edu.co.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    @RabbitListener(queues = "payments-dlq")
    public void handleFailedPayment(String message) {
        // Lógica para manejar los pagos fallidos
        System.out.println("Pago fallido recibido en DLQ: " + message);
        // Aquí puedes implementar lógica adicional para alertar o procesar el mensaje
    }
}
