package com.example.testproject.domain.user.repository;

import com.example.testproject.domain.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

    boolean existsByUsername(String username);

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    @Transactional
    void deleteByUsername(String username);

}
