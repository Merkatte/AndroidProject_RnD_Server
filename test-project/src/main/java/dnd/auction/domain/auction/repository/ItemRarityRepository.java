package dnd.auction.domain.auction.repository;

import dnd.auction.domain.auction.entity.ItemRarity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRarityRepository extends JpaRepository<ItemRarity, Long> {

    ItemRarity findByName(String name);

}
