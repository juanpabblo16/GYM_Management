package com.icesi.edu.co.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @RabbitListener(queues = "schedule-change-queue")
    public void handleScheduleChange(String message) {
        // Lógica para manejar cambios en horarios
        System.out.println("Notificación de cambio de horario recibida: " + message);
    }
}
