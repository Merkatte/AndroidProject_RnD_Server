package com.example.testproject.domain.post.dto;

import com.example.testproject.domain.post.entity.Comment;
import com.example.testproject.domain.user.dto.UserResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDTO {
    Long commentId;
    UserResponseDTO user;
    String contents;
    LocalDateTime createdAt;

    @Builder
    public CommentResponseDTO(Comment comment){
        this.commentId = comment.getId();
        this.user = new UserResponseDTO(comment.getAppUser());
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
    }

}
