package com.example.testproject.domain.post.service;

import com.example.testproject.domain.post.dto.PostDTO;
import com.example.testproject.domain.post.dto.PostResponseDTO;
import com.example.testproject.domain.post.entity.Post;
import com.example.testproject.domain.post.entity.Timeline;
import com.example.testproject.domain.post.repository.PostRepository;
import com.example.testproject.domain.post.repository.TimelineRepository;
import com.example.testproject.domain.user.exception.CustomException;
import com.example.testproject.domain.user.repository.FollowRepository;
import com.example.testproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostWriteService {

    final private PostRepository postRepository;
    final private UserRepository userRepository;
    private final FollowRepository followRepository;
    private final TimelineRepository timelineRepository;

    @Transactional
    public PostResponseDTO createPost(PostDTO command){

        var appUser = userRepository.findById(command.userId()).orElseThrow();
        var post = Post.builder()
                .appUser(appUser).title(command.title()).contents(command.contents()).build();
        var createPost = postRepository.save(post);

        /**
         * 게시글 작성 유저를 팔로우한 모든 유저에게
         * timeline entity 통해 게시글 id를 delivery.
         * PushModel : write 시에 부하 부담.
         */
        var followers = followRepository.findUserIdByFollowId(createPost.getAppUser().getId());
        List<Timeline> timelines = followers.stream().map(f-> Timeline.builder()
                        .postId(createPost.getId())
                        .userId(f)
                .build()).toList();
        timelineRepository.saveAll(timelines);

        return new PostResponseDTO(createPost);
    }

    @Transactional
    public PostResponseDTO updatePost(PostDTO command, Long postId){

        var post = this.postRepository.findById(postId).orElseThrow();

        if (!Objects.equals(post.getAppUser().getId(), command.userId())){
            throw new CustomException("No Permission to Update", HttpStatus.BAD_REQUEST);
        }
        var updatePost = postRepository.save(Post.builder()
                        .id(post.getId())
                        .appUser(post.getAppUser())
                        .createdDate(post.getCreatedDate())
                        .title(command.title())
                        .contents(command.contents())
                        .createdAt(LocalDateTime.now())
                .build());

        return new PostResponseDTO(updatePost);
    }
}
