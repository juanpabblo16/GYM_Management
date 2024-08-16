package com.icesi.edu.co.controller;

import com.icesi.edu.co.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.icesi.edu.co.model.Class;

import java.util.List;

@RestController
@RequestMapping("/api/gym")
public class ClassController {
    @Autowired
    private ClassService gymService;

    @PostMapping("/class")
    public Class programClass(@RequestBody Class cl) {
        return gymService.programClass(cl);
    }

    @GetMapping("/class")
    public List<Class> getAllClass() {
        return gymService.getAllClass();
    }

}
