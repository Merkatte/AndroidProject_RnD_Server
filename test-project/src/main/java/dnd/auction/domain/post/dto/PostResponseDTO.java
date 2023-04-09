package dnd.auction.domain.post.dto;

import dnd.auction.domain.user.dto.UserResponseDTO;
import dnd.auction.domain.post.entity.Image;
import dnd.auction.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDTO{
    Long postId;
    UserResponseDTO user;
    String title;
    String contents;
    LocalDateTime createdAt;
    List<CommentResponseDTO> comments;
    List<ImageResponseDTO> images;
    String postCategory;

    @Builder
    public PostResponseDTO(Post post){
        this.postId = post.getId();
        this.user = new UserResponseDTO(post.getAppUser());
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.comments = post.getComments().stream().map(CommentResponseDTO::new).collect(Collectors.toList());
        this.images = post.getImages().stream().map(ImageResponseDTO::new).collect(Collectors.toList());
        this.postCategory = post.getPostCategory().getName();
    }

    @Builder
    public PostResponseDTO(Post post, List<Image> images){
        this.postId = post.getId();
        this.user = new UserResponseDTO(post.getAppUser());
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.comments = post.getComments().stream().map(CommentResponseDTO::new).collect(Collectors.toList());
        this.images = images.stream().map(ImageResponseDTO::new).collect(Collectors.toList());
        this.postCategory = post.getPostCategory().getName();
    }

}
