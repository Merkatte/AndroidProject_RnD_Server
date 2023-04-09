package dnd.auction.domain.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CommentLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long userId;

    @Column(nullable = false)
    Long commentId;

    @Builder
    public CommentLikes(Long id, Long userId, Long commentId) {
        this.id = id;
        this.userId = userId;
        this.commentId = commentId;
    }
}
