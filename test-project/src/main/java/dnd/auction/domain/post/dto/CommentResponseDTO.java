package dnd.auction.domain.post.dto;

import dnd.auction.domain.post.entity.Comment;
import dnd.auction.domain.user.dto.UserResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDTO {
    Long commentId;
    UserResponseDTO user;
    String contents;
    LocalDateTime createdAt;

    @Builder
    public CommentResponseDTO(Comment comment){
        this.commentId = comment.getId();
        this.user = new UserResponseDTO(comment.getAppUser());
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
    }

}
