package com.example.testproject.domain.user.repository;

import com.example.testproject.domain.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long>{
    Boolean existsByUserIdAndFollowId(Long userId, Long followId);
    Follow findByUserIdAndFollowId(Long userId, Long followId);

    void deleteByUserIdAndFollowId(Long userId, Long followId);
}
