package com.icesi.edu.co.controller;

import com.icesi.edu.co.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.icesi.edu.co.model.Class;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/gym")
public class ClassController {

    @Autowired
    private ClassService gymService;

    @PostMapping("/class")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Programar una nueva clase",
            description = "Permite a un entrenador programar una nueva clase en el sistema."
    )
    public Class programClass(@RequestBody Class cl) {
        return gymService.programClass(cl);
    }

    @GetMapping("/class")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(
            summary = "Obtener todas las clases",
            description = "Recupera una lista de todas las clases programadas en el gimnasio."
    )
    public List<Class> getAllClass() {
        return gymService.getAllClass();
    }
}
