package com.icesi.edu.co.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.icesi.edu.co.dto.ReservationDTO;
import com.icesi.edu.co.model.Class;
import com.icesi.edu.co.repository.ClassRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public List<Class> getAllClass() {
        return classRepository.findAll();
    }

    public Class updateClass(Class updatedClass) {
        Class savedClass = classRepository.save(updatedClass);
        rabbitTemplate.convertAndSend("notification-exchange", "",
                "Horario de clase actualizado: " + savedClass.getName());
        return savedClass;
    }

    public void notifyScheduleChange(String classDetails) {
        rabbitTemplate.convertAndSend("schedule-change-exchange", "", classDetails);
    }

    public Class programClass(Class cl) {
        Boolean trainerAvalaible = restTemplate.getForObject(
                "http://localhost:8081/api/gym/trainer/" + cl.getIdTrainer().getIdTrainer(), Boolean.class);

        if (!trainerAvalaible) {
            throw new RuntimeException("El entrenador no existe o no está disponible");
        }

        Class savedClass = classRepository.save(cl);
        notifyScheduleChange("Nueva clase programada: " + savedClass.getName());

        return savedClass;
    }

    public Class reserveClass(ReservationDTO reservationDTO) {

        Optional<Class> optionalClass = classRepository.findById(reservationDTO.getClassId());

        Boolean trainerAvalaible = restTemplate.getForObject(
                "http://localhost:8082/api/members/exist/" + reservationDTO.getMemberId(), Boolean.class);

        if (!trainerAvalaible) {
            throw new RuntimeException("El miembro no existe o no está disponible");
        }

        if (!optionalClass.isPresent()) {
            throw new RuntimeException("La clase no existe");
        }

        Class cl = optionalClass.get();
        if (cl.getCurrentCapacity() >= cl.getMaximumCapacity()) {
            throw new RuntimeException("La clase está llena");
        }

        cl.setCurrentCapacity(cl.getCurrentCapacity() + 1);
        Class savedClass = classRepository.save(cl);

        String message = "Reserva de clase exitosa: " + savedClass.getName() + " - " + LocalDate.now();
        kafkaTemplate.send("ocupacion-clases", message);
        return savedClass;

    }

    @KafkaListener(topics = "ocupacion-clases", groupId = "monitoreo-grupo")
    public void consumirActualizacionOcupacion(String ocupacion) {
        System.out.println("Mensaje recibido: " + ocupacion);
    }

    
}
