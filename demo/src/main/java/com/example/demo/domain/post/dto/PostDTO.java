package com.example.demo.domain.post.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class PostDTO {
    Long id;
    Long userId;
    String contents;
    LocalDateTime createdAt;

    @Builder
    public PostDTO(Long id, Long userId, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.contents = contents;
        this.createdAt = createdAt;
    }
}
