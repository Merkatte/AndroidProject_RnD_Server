package dnd.auction.domain.auction.repository;

import dnd.auction.domain.auction.entity.ItemClasses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemClassesRepository extends JpaRepository<ItemClasses, Long> {
}
