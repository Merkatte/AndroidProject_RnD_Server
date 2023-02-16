package com.example.demo.domain.follow;

import com.example.demo.domain.follow.entity.Follow;
import com.example.demo.domain.follow.repository.FollowRepository;
import com.example.demo.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

@SpringBootTest
public class FollowBulkInsertTest {

    @Autowired
    private FollowRepository followRepository;

    @Test
    public void followBulkInsert(){
        for (long i=2; i<=10000*5; i++){
            followRepository.save(
                    Follow.builder()
                            .userId(i)
                            .followId(1L)
                            .build()

            );
        }
    }

    @Test
    public void followingsInsert(){
        for (long followId=2; followId<=1001; followId++){
            followRepository.save(
                    Follow.builder()
                            .followId(followId)
                            .userId(1L)
                            .build()
            );
        }
    }
}
