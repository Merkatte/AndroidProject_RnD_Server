package dnd.auction.domain.auction.repository;

import dnd.auction.domain.auction.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassesRepository extends JpaRepository<Classes, Long> {

    Classes findByName(String name);
}
