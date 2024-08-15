package com.icesi.edu.co.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icesi.edu.co.entity.Trainer;
import com.icesi.edu.co.service.TrainerService;

@RestController
@RequestMapping("/api/gym")
public class TrainerController {
    @Autowired
    private TrainerService trainerService;

    @PostMapping("/trainer")
    public Trainer addTrainer(@RequestBody Trainer trainer) {
        return trainerService.addTrainer(trainer);
    }
    
    @GetMapping("/trainer")
    public List<Trainer> getAllTrainers() {
        return trainerService.getAllTrainers();
    }
}
