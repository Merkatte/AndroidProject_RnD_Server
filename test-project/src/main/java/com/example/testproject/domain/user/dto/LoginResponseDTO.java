package com.example.testproject.domain.user.dto;

import com.example.testproject.domain.user.entity.AppUserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class LoginResponseDTO{
    String refreshToken;
    String accessToken;
    Long userId;
    String username;
    LocalDateTime loginAt;
    List<AppUserRole> appUserRoles;

    @Builder
    public LoginResponseDTO(String refreshToken, String accessToken, Long userId, String username, LocalDateTime loginAt, List<AppUserRole> appUserRoles) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.loginAt = loginAt;
        this.appUserRoles = appUserRoles;
    }
}
