package dnd.auction.domain.post.repository;

import dnd.auction.domain.post.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    PostCategory findByName(String name);
}
