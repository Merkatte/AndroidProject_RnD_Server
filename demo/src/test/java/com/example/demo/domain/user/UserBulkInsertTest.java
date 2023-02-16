package com.example.demo.domain.user;


import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class UserBulkInsertTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userBulkInsert(){
        int size = 10000*10;
        for (int i = 0; i < size; i++){
            userRepository.save(
                    User.builder()
                            .username(UUID.randomUUID().toString())
                            .build()
            );
        }
    }
}
