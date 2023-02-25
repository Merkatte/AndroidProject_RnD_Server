package com.example.testproject.domain.user.repository;

import com.example.testproject.domain.user.entity.AppUser;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

    boolean existsByUsername(String username);

    boolean existsById(Long userId);


    AppUser findByEmail(String email);

    Optional<AppUser> findById(Long id);

    Optional<AppUser> findByUsername(String username);

    AppUser findEmailById(Long id);

    @Transactional
    void deleteByUsername(String username);

    @Modifying
    @Query("UPDATE AppUser u SET u.lastLogin = :lastLogin where u.id = :id")
    void updateUserLastLogin(@Param(value="lastLogin")LocalDateTime lastLogin, @Param(value="id") Long id);

}
