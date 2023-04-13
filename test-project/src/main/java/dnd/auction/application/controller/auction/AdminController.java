package dnd.auction.application.controller.auction;

import dnd.auction.domain.auction.dto.request.*;
import dnd.auction.domain.auction.entity.*;
import dnd.auction.domain.auction.service.AdminWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auction/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminWriteService adminWriteService;

    // ADMIN Insert/Update Item Categories
    @PostMapping("/item-type")
    public ItemType registerItemType(@RequestBody ItemTypeRequestDTO itemTypeCommand){
        return adminWriteService.registerItemType(itemTypeCommand.name(), itemTypeCommand.partsName());
    }

    @PostMapping("/item-rarity")
    public ItemRarity registerItemType(@RequestBody ItemRarityRequestDTO itemRarityCommand){
        return adminWriteService.registerItemRarity(itemRarityCommand);
    }

    @PostMapping("/item-parts")
    public ItemParts registerItemParts(@RequestBody ItemPartsRequestDTO itemPartsCommand){
        return adminWriteService.registerItemParts(itemPartsCommand.name());
    }
    @PostMapping("/classes")
    public Classes regusterClasses(@RequestBody ClassesRequestDTO classesCommand){
        return adminWriteService.registerClasses(classesCommand.name());
    }
    @PostMapping("/item")
    public Item registerItem(@RequestBody ItemRequestDTO itemDTO){
        return adminWriteService.registerItem(itemDTO);
    }
}
