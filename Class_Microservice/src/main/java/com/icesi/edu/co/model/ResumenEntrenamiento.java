package com.icesi.edu.co.model;


import java.util.ArrayList;
import java.util.List;


public class ResumenEntrenamiento {
    
    private IdMember idMember;
    private int totalClasses;

    private List<String> notes;

    public ResumenEntrenamiento() {
        this.totalClasses = 0;
        this.notes = new ArrayList<>();
    }

    public ResumenEntrenamiento actualizar(String value) {
        this.totalClasses += 1;
        notes.add(value);
        return this;
    }
}
