package com.example.testproject.domain.post.service;

import com.example.testproject.domain.post.dto.CommentDTO;
import com.example.testproject.domain.post.entity.Comment;
import com.example.testproject.domain.post.repository.CommentRepository;
import com.example.testproject.domain.post.repository.PostRepository;
import com.example.testproject.exception.CustomException;
import com.example.testproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentWriteService {
    final private CommentRepository commentRepository;
    final private UserRepository userRepository;
    final private PostRepository postRepository;

    public Long createComment(CommentDTO command){
        var appUser = userRepository.findById(command.userId()).orElseThrow();
        var post = postRepository.findById(command.postId()).orElseThrow();
        var createComment = commentRepository.save(
                Comment.builder()
                .appUser(appUser)
                .post(post)
                .contents(command.contents())
                .build());

        return createComment.getPost().getId();
    }

    public Long updateComment(CommentDTO command, Long commentId){
        var comment = commentRepository.findById(commentId).orElseThrow();
        if (!Objects.equals(command.userId(), comment.getAppUser().getId())){
            throw new CustomException("No Permission to Update", HttpStatus.BAD_REQUEST);
        }
        var updateComment = commentRepository.save(Comment.builder()
                .id(commentId)
                .appUser(comment.getAppUser())
                .post(comment.getPost())
                .contents(command.contents())
                .createdAt(LocalDateTime.now()).build());

        return updateComment.getPost().getId();
    }
}
