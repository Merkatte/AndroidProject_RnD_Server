package com.example.demo.domain.post;

import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostService postService;
    @Test
    public void postBulkInsert(){
        int count = 100000;
        for (long userId = 2; userId <= 1001; userId++){
            for(int i = 0; i < 10; i++){
                count ++;
                var post = Post.builder()
                        .contents("test Content " + count)
                        .userId(userId)
                        .build();
                postService.createPost(post);
            }
        }

    }


}
