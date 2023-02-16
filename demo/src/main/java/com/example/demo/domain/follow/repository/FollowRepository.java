package com.example.demo.domain.follow.repository;

import com.example.demo.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query(value = "SELECT follow_id FROM follow WHERE user_id = :user_id", nativeQuery = true)
    List<Long> findFollowIdByUserId(@Param("user_id") Long userId);
}
