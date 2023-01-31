package com.example.testproject.domain.user.dto;

import java.time.LocalDateTime;

public record LoginResponseDTO(
        String refreshToken,
        String accessToken,
        Long userId,
        String username,
        LocalDateTime loginAt
) {
}
