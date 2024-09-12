package com.icesi.edu.co.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icesi.edu.co.model.Class;
import com.icesi.edu.co.repository.ClassRepository;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public List<Class> getAllClass() {
        return classRepository.findAll();
    }

    public Class updateClass(Class updatedClass) {
        Class savedClass = classRepository.save(updatedClass);
        // Enviar notificación de cambio
        rabbitTemplate.convertAndSend("notification-exchange", "", "Horario de clase actualizado: " + savedClass.getName());
        return savedClass;
    }

    // Publicar notificación de cambios en horarios
    public void notifyScheduleChange(String classDetails) {
        rabbitTemplate.convertAndSend("schedule-change-exchange", "", classDetails);
    }

    public Class programClass(Class cl) {
        Class savedClass = classRepository.save(cl);
        // Notificar sobre el nuevo horario
        notifyScheduleChange("Nueva clase programada: " + savedClass.getName());
        return savedClass;
    }
}
