package dnd.auction.domain.post.repository;

import dnd.auction.domain.post.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {
    @Query("SELECT postId FROM Timeline WHERE userId = :userId")
    List<Long> findPostIdByUserId(@Param("userId") Long userId);

}
