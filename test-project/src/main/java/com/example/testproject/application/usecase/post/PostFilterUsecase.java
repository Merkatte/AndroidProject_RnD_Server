package com.example.testproject.application.usecase.post;

import com.example.testproject.domain.post.service.PostReadService;
import com.example.testproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFilterUsecase {

    final private UserRepository userRepository;
    final private PostReadService postReadService;
//    public List<PostResponseDTO> execute(FilterKeywordDTO filterKeyword, Pageable pageable){
//
//    }
}
