package com.example.testproject.application.controller;

import com.example.testproject.domain.post.dto.PostDTO;
import com.example.testproject.domain.post.dto.PostResponseDTO;
import com.example.testproject.domain.post.service.PostReadService;
import com.example.testproject.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    final private PostWriteService postWriteService;
    final private PostReadService postReadService;

    @PostMapping("/create")
    public PostResponseDTO createPost(@RequestBody PostDTO post){
        return postWriteService.createPost(post);
    }

    @PostMapping("/update/{postId}")
    public PostResponseDTO updatePost(@RequestBody PostDTO post, @PathVariable Long postId){
        return postWriteService.updatePost(post, postId);
    }
    @GetMapping("/{postId}")
    public PostResponseDTO getPost(@PathVariable Long postId){
        return postReadService.getPost(postId);
    }

    @GetMapping("/timeline/{userId}")
    public List<PostResponseDTO> getTimeline(@PathVariable Long userId){
        return postReadService.getTimeline(userId);
    }

}
