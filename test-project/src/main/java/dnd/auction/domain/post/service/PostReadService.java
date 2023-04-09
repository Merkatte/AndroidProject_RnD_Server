package dnd.auction.domain.post.service;

import dnd.auction.domain.post.repository.TimelineRepository;
import dnd.auction.domain.post.dto.PostResponseDTO;
import dnd.auction.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostReadService {
    final private PostRepository postRepository;
    final private TimelineRepository timelineRepository;

    public PostResponseDTO getPost(Long postId){
        var post = postRepository.findById(postId).orElseThrow();
        return new PostResponseDTO(post);
    }

    public List<PostResponseDTO> getTimeline(Long userId){
        var postIds = timelineRepository.findPostIdByUserId(userId);
        var posts = postRepository.findByIdIn(postIds);
        return posts.stream().map(p->PostResponseDTO.builder().post(p).build()).toList();
    }

    public List<PostResponseDTO> getBoard(Pageable pageable){
        var posts = postRepository.findAll(pageable).getContent();
        return posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getBoardByCategory(String categoryName, Pageable pageable){
        var posts = postRepository.findByPostCategoryName(categoryName, pageable);
        return posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());
        //TODO Pagination 구현 필요
    }

    public List<PostResponseDTO> getBoardByUserId(Long userId, Pageable pageable){
        var posts = postRepository.findByAppUserId(userId, pageable);
        return posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());
        //TODO Pagination 구현 필요
    }

}
