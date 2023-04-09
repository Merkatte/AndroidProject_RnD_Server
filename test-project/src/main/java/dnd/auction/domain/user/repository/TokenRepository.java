package dnd.auction.domain.user.repository;

import dnd.auction.domain.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, String> {
}
