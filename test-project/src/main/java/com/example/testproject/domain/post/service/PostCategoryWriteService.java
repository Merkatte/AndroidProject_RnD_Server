package com.example.testproject.domain.post.service;

import com.example.testproject.domain.post.dto.PostCategoryDTO;
import com.example.testproject.domain.post.entity.PostCategory;
import com.example.testproject.domain.post.repository.PostCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCategoryWriteService {

    final private PostCategoryRepository postCategoryRepository;
    public PostCategory createPostCategory(PostCategoryDTO postCategoryDTO){
        return postCategoryRepository.save(PostCategory.builder().name(postCategoryDTO.name()).build());
    }

}
