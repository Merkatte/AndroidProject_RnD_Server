package com.example.testproject.application.controller;

import com.example.testproject.domain.post.dto.CommentDTO;
import com.example.testproject.domain.post.dto.LikesDTO;
import com.example.testproject.domain.post.service.CommentWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    final private CommentWriteService commentWriteService;
    @PostMapping("/create")
    public Long createComment(@RequestBody CommentDTO comment){
        return commentWriteService.createComment(comment);
    }

    @PostMapping("/update/{commentId}")
    public Long updateComment(@RequestBody CommentDTO command, @PathVariable Long commentId){
        return commentWriteService.updateComment(command, commentId);
    }

    @PostMapping("/likes/{commentId}")
    public void likesComment(@PathVariable("commentId") Long commentId, @RequestBody LikesDTO likesDTO){
        commentWriteService.likesComment(commentId, likesDTO.userId());
    }
}
