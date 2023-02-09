package com.example.testproject.domain.user.dto;

import com.example.testproject.domain.user.entity.AppUser;
import com.example.testproject.domain.user.entity.AppUserRole;

import java.time.LocalDateTime;
import java.util.List;


public class LoginResponseDTO{
        String refreshToken;
        String accessToken;
        Long userId;
        String username;
        LocalDateTime loginAt;
        List<AppUserRole> appUserRoles;

        public LoginResponseDTO(String refreshToken, String accessToken, AppUser user){
            this.refreshToken = refreshToken;
            this.accessToken = accessToken;
            this.userId = user.getId();
            this.username = user.getUsername();
            this.loginAt = user.getLastLogin();
            this.appUserRoles = user.getAppUserRoles();
        }
}
