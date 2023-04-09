package dnd.auction.domain.auction.repository;

import dnd.auction.domain.auction.entity.ItemParts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPartsRepository extends JpaRepository<ItemParts, Long> {

    ItemParts findByName(String name);

}
