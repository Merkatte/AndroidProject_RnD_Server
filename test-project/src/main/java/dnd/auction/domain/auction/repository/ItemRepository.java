package dnd.auction.domain.auction.repository;

import dnd.auction.domain.auction.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByName(String name);

    Optional<Item> findById(Long id);


}
