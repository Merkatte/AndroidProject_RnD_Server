package com.example.testproject.domain.post.repository;

import com.example.testproject.domain.post.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    PostLikes findByUserIdAndPostId(Long userId, Long postId);

}
