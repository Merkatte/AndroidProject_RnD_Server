package com.example.demo.domain.post.service;

import com.example.demo.domain.follow.entity.Follow;
import com.example.demo.domain.follow.repository.FollowRepository;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.entity.Timeline;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.post.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    final private PostRepository postRepository;
    final private TimelineRepository timelineRepository;
    private final FollowRepository followRepository;

    public void createPost(Post post){
        var newPost = postRepository.save(post);
        var followers = followRepository.findFollowIdByUserId(post.getUserId());
        List<Timeline> timeline = new ArrayList<>();
        for (Long followerId : followers){
            timeline.add(Timeline.builder()
                    .userId(followerId)
                    .post(post)
                    .build());
        };
        timelineRepository.saveAll(timeline);
    }

}
