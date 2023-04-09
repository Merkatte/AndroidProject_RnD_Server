package dnd.auction.domain.post.service;

import dnd.auction.domain.post.dto.PostCategoryDTO;
import dnd.auction.domain.post.entity.PostCategory;
import dnd.auction.domain.post.repository.PostCategoryRepository;
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
