package com.icesi.edu.co.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime schedule;

    private int maximumCapacity;

    private int currentCapacity;

    @Embedded
    private IdTrainer idTrainer;
}