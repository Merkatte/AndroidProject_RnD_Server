package com.example.testproject.domain.auction.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemRequestDTO {

    String name;
    String itemType;
    String itemParts;
    List<String> itemClasses;

    @Builder
    public ItemRequestDTO(String itemType, String itemParts, List<String> itemClasses) {
        this.itemType = itemType;
        this.itemParts = itemParts;
        this.itemClasses = itemClasses;
    }
}
