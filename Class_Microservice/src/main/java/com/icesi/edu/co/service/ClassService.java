package com.icesi.edu.co.service;

import com.icesi.edu.co.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.icesi.edu.co.model.Class;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public Class programClass(Class cl) {
        return classRepository.save(cl);
    }

    public List<Class> getAllClass() {
        return classRepository.findAll();
    }

}
