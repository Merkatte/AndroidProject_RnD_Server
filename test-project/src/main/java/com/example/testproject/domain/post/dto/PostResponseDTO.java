package com.example.testproject.domain.post.dto;

import com.example.testproject.domain.post.entity.Post;
import com.example.testproject.domain.user.dto.UserResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDTO{
    Long postId;
    UserResponseDTO user;
    String title;
    String contents;
    LocalDateTime createdAt;
    List<CommentResponseDTO> comments;

    @Builder
    public PostResponseDTO(Post post){
        this.postId = post.getId();
        this.user = new UserResponseDTO(post.getAppUser());
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.comments = post.getComments().stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }

}
