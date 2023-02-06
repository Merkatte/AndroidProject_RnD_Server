package com.example.testproject.domain.post.service;

import com.example.testproject.domain.post.dto.PostResponseDTO;
import com.example.testproject.domain.post.repository.PostRepository;
import com.example.testproject.domain.post.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReadService {
    final private PostRepository postRepository;
    final private TimelineRepository timelineRepository;

    public PostResponseDTO getPost(Long postId){
        var post = postRepository.findById(postId).orElseThrow();
        return new PostResponseDTO(post);
    }

    public List<PostResponseDTO> getTimeline(Long userId){
        var postIds = timelineRepository.findPostIdByUserId(userId);
        var posts = postRepository.findByIdIn(postIds);
        return posts.stream().map(p->PostResponseDTO.builder().post(p).build()).toList();
    }
}
