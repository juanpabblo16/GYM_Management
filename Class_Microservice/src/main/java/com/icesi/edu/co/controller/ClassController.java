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

    @PostMapping("/clases")
    public Class programarClase(@RequestBody Class clase) {
        return gymService.programClass(clase);
    }

    @GetMapping("/clases")
    public List<Class> obtenerTodasClases() {
        return gymService.getAllClass();
    }

}
