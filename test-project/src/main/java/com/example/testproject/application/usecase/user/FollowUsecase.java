package com.example.testproject.application.usecase.user;

import com.example.testproject.domain.user.entity.Follow;
import com.example.testproject.exception.CustomException;
import com.example.testproject.domain.user.repository.FollowRepository;
import com.example.testproject.domain.user.repository.UserRepository;
import com.example.testproject.domain.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowUsecase {

    final private UserRepository userRepository;
    final private FollowRepository followRepository;
    final private FollowService followService;

    @Transactional
    public void execute(Follow follow){
        if (!userRepository.existsById(follow.getUserId()) || !userRepository.existsById(follow.getFollowId())){
            throw new CustomException("User Does Not Exist", HttpStatus.UNPROCESSABLE_ENTITY);
        } // exception User Not Found
        if (followRepository.existsByUserIdAndFollowId(follow.getUserId(), follow.getFollowId())){
            followService.deleteFollow(follow);
        }
        else {
            followService.createFollow(follow);
        }
    }
}
