package com.example.demo.domain.post.repository;

import com.example.demo.domain.post.dto.PostDTO;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findByUserId(Long userId);

    @Query(value = "SELECT post_id FROM timeline WHERE user_id = :userId", nativeQuery = true)
    List<Long> findPostIdByUserId(@Param("userId") Long userId);

}
