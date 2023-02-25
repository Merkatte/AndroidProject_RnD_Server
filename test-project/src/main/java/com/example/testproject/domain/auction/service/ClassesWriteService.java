package com.example.testproject.domain.auction.service;

import com.example.testproject.domain.auction.entity.Classes;
import com.example.testproject.domain.auction.repository.ClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassesWriteService {

    final private ClassesRepository classesRepository;

    public Classes registerClasses(String classes){
        return classesRepository.save(Classes.builder().name(classes).build());
    }
}
