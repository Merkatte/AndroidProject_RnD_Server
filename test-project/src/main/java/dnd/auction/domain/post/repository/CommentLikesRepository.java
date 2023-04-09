package dnd.auction.domain.post.repository;

import dnd.auction.domain.post.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    CommentLikes findByUserIdAndCommentId(Long userId, Long commentId);

}
