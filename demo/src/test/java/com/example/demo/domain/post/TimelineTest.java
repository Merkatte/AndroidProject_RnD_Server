package com.example.demo.domain.post;

import com.example.demo.domain.follow.repository.FollowRepository;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.post.repository.TimelineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
public class TimelineTest {

    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private PostRepository postRepository;


    @Test
    public void timelinePushModel(){
        var stopWatch = new StopWatch();
        stopWatch.start();
        var postIds = timelineRepository.findPostIdByUserId(1L);
        var posts = postRepository.findByIdIn(postIds);
        stopWatch.stop();
        System.out.println("타임라인 쿼리 시간 " + stopWatch.getTotalTimeMillis() + "ms");

    }


    @Test
    public void timelinePullModel(){
        var stopWatch = new StopWatch();
        stopWatch.start();
        var followIds = followRepository.findFollowIdByUserId(1L);
        var posts = postRepository.findByUserIdIn(followIds);
        stopWatch.stop();
        System.out.println("타임라인 쿼리 시간 " + stopWatch.getTotalTimeMillis() + "ms");
    }
}
