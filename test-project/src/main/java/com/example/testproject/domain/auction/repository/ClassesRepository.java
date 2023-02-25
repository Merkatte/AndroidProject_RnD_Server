package com.example.testproject.domain.auction.repository;

import com.example.testproject.domain.auction.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassesRepository extends JpaRepository<Classes, Long> {

    Classes findByName(String name);
}
