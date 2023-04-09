package dnd.auction.domain.post.repository;

import dnd.auction.domain.post.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

    PostLikes findByUserIdAndPostId(Long userId, Long postId);

}
