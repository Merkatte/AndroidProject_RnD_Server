package dnd.auction.domain.auction.dto.response;

import dnd.auction.domain.auction.entity.Item;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemResponseDTO {

    String name;
    String type;
    String parts;
    List<String> classes;

    public ItemResponseDTO(Item item){
        this.name = item.getName();
        this.type = item.getItemType().getName();
        this.parts = item.getItemParts().getName();
        this.classes = item.getItemClasses().stream().map(c -> c.getClasses().getName()).toList();
    }
}
