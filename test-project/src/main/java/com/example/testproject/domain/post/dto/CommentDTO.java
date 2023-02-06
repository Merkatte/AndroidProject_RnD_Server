package com.example.testproject.domain.post.dto;

public record CommentDTO(
        Long userId,
        Long postId,
        String contents
) {
}
