package com.example.testproject.domain.user.service;

import com.example.testproject.domain.user.entity.Follow;
import com.example.testproject.domain.user.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    final private FollowRepository followRepository;

    public void createFollow(Follow follow){
        followRepository.save(follow);
    }
    public void deleteFollow(Follow follow){
        followRepository.deleteByUserIdAndFollowId(follow.getUserId(), follow.getFollowId());
    }
}
