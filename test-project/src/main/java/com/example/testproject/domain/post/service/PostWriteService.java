package com.example.testproject.domain.post.service;

import com.example.testproject.domain.post.dto.PostDTO;
import com.example.testproject.domain.post.dto.PostResponseDTO;
import com.example.testproject.domain.post.entity.Image;
import com.example.testproject.domain.post.entity.Post;
import com.example.testproject.domain.post.entity.Timeline;
import com.example.testproject.domain.post.repository.ImageRepository;
import com.example.testproject.domain.post.repository.PostRepository;
import com.example.testproject.domain.post.repository.TimelineRepository;
import com.example.testproject.exception.CustomException;
import com.example.testproject.domain.user.repository.FollowRepository;
import com.example.testproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Component
public class PostWriteService {

    final private PostRepository postRepository;
    final private UserRepository userRepository;
    final private FollowRepository followRepository;
    final private TimelineRepository timelineRepository;
    final private ImageRepository imageRepository;

    @Value("${static.path}")
    private String rootPath;

    @Transactional
    public PostResponseDTO createPost(PostDTO command, List<MultipartFile> images){

        var appUser = userRepository.findById(command.userId()).orElseThrow();
        var post = Post.builder()
                .appUser(appUser).title(command.title()).contents(command.contents()).build();
        var createPost = postRepository.save(post);

        // Timeline PushModel 구현
        /* Timeline PushModel 구현
          게시글 작성 유저를 팔로우한 모든 유저에게
          timeline entity 통해 게시글 id를 delivery.
          PushModel : write 시에 부하 부담.
         */
        var followers = followRepository.findUserIdByFollowId(createPost.getAppUser().getId());
        List<Timeline> timelines = followers.stream().map(f-> Timeline.builder()
                        .postId(createPost.getId())
                        .userId(f)
                .build()).toList();
        timelineRepository.saveAll(timelines);

        // 이미지 업로드 구현 (다중 이미지)
        Path dir = getPath(rootPath); // 이미지 저장 디렉토리 path
        Path url = getPath("localhost:8080/static"); // 이미지 URL path
        var newImages = images.stream().map(i-> saveImage(i, url, dir, createPost)).toList();

        return new PostResponseDTO(createPost, newImages);
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
    } // TODO updatePost 이미지 수정 로직 추가 필요.... 현재 update 이미지 수정 불가...

    // --------------- 이미지 업로드 로직 ---------------//
    private Image saveImage(MultipartFile images, Path url, Path dir, Post post) {
        //원래 파일이름 앞에 uuid를 추가시킴
        var fileName = images.getOriginalFilename();
        var saveFileName = uuidFileName(fileName);

        try {
            Files.createDirectories(dir); //경로에 폴더가 없을 경우 생성함. exception 발생하지 않음
            Path targetPath = dir.resolve(saveFileName).normalize(); // 파일이름이 포함된 directory 경로를 추가
            String urlPath = url.resolve(saveFileName).normalize().toString(); // 파일 이름이 포함된 url 경로 추가
            if (Files.exists(targetPath)) {
                throw new CustomException("Failed due to duplicate files.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            images.transferTo(targetPath);
            return imageRepository.save(Image.builder()
                    .post(post)
                    .name(saveFileName)
                    .path(urlPath).build());
        } catch (CustomException | IOException e) {
            e.printStackTrace();
            throw new CustomException("Failed due to duplicate files.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private Path getPath(String path) {
        Date createDate = new Date();
        var year = (new SimpleDateFormat("yyyy").format(createDate)); //년도
        var month = (new SimpleDateFormat("MM").format(createDate)); //월
        var day = (new SimpleDateFormat("dd").format(createDate)); //일

        return Paths.get(path, year, month, day);
    }
    private String uuidFileName(String originalFileName) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + '_' + originalFileName;
    }
}
