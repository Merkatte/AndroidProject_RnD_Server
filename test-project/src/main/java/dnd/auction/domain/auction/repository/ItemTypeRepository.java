package dnd.auction.domain.auction.repository;

import dnd.auction.domain.auction.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

    ItemType findByName(String name);

}
