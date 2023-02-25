package com.example.testproject.domain.post.repository;

import com.example.testproject.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByIdIn(List<Long> id);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByPostCategoryName(String name, Pageable pageable);

    Page<Post> findByAppUserId(Long userId, Pageable pageable);
}
