package com.example.testproject.domain.auction.specification;

import com.example.testproject.domain.auction.entity.AuctionItems;
import com.example.testproject.domain.auction.entity.Item;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class AuctionItemsSpecification {
    public static Specification<AuctionItems> greaterThanOptionValue(String option, Float value){
        return new Specification<AuctionItems>() {
            @Override
            public Predicate toPredicate(Root<AuctionItems> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(option), value);
            }
        };
    }

    public static Specification<AuctionItems> equalItemCategory(String option, Long value){
        return new Specification<AuctionItems>() {
            @Override
            public Predicate toPredicate(Root<AuctionItems> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Item, AuctionItems> categorizedItem = root.join("item");
                return criteriaBuilder.equal(categorizedItem.get(option), value);
            }
        };
    }

    public static Specification<AuctionItems> equalItemName(String key, Item item){
        return new Specification<AuctionItems>() {
            @Override
            public Predicate toPredicate(Root<AuctionItems> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(key), item);
            }
        };
    }
}
