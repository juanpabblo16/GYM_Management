package com.icesi.edu.co.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classReservation;

    @Embedded
    private IdMember idMember;

}
