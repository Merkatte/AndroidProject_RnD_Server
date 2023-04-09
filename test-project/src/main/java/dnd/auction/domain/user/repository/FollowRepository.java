package dnd.auction.domain.user.repository;

import dnd.auction.domain.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long>{
    Boolean existsByUserIdAndFollowId(Long userId, Long followId);
    Follow findByUserIdAndFollowId(Long userId, Long followId);

    void deleteByUserIdAndFollowId(Long userId, Long followId);

    @Query("SELECT userId FROM Follow where followId = :followId ")
    List<Long> findUserIdByFollowId(@Param("followId") Long followId);
}
