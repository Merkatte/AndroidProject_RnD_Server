package com.example.testproject.domain.user.repository;

import com.example.testproject.domain.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, String> {
}
