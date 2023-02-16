package com.example.testproject.domain.post.repository;

import com.example.testproject.domain.post.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    PostCategory findByName(String name);
}
