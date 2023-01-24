package com.example.testproject.domain.user.dto;

import com.example.testproject.domain.user.entity.AppUserRole;

import java.util.List;

public record UserDataDTO(
        String username,
        String email,
        String password,
        List<AppUserRole> appUserRole

) {
}
