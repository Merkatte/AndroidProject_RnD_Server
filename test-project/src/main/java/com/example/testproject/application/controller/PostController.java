package com.example.testproject.application.controller;

import com.example.testproject.domain.post.dto.PostDTO;
import com.example.testproject.domain.post.dto.PostResponseDTO;
import com.example.testproject.domain.post.service.PostReadService;
import com.example.testproject.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    final private PostWriteService postWriteService;
    final private PostReadService postReadService;

    @PostMapping(value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDTO updatePost(@RequestPart PostDTO post, @RequestPart List<MultipartFile> images){
        return postWriteService.createPost(post, images);
    }

    @PostMapping(value = "/update/{postId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDTO updatePost(@RequestPart PostDTO post, @RequestPart MultipartFile images,
                                      @PathVariable Long postId){
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