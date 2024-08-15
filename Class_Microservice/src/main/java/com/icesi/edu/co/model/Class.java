package com.icesi.edu.co.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import com.icesi.edu.co.model.Trainer;

@Data
@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime schedule;
    private int maximumCapacity;

    @ManyToOne
    private Trainer trainer;
}