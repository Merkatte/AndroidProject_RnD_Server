package com.example.testproject.domain.auction.specification;

import com.example.testproject.domain.auction.entity.AuctionItems;
import com.example.testproject.domain.auction.entity.Item;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AuctionItemsSpecification {
    public static Specification<AuctionItems> greaterThanOptionValue(String option, Float value){
        return new Specification<AuctionItems>() {
            @Override
            public Predicate toPredicate(Root<AuctionItems> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(option), value);
            }
        };
    }

    public static Specification<AuctionItems> equalSearchKey(String key, Item item){
        return new Specification<AuctionItems>() {
            @Override
            public Predicate toPredicate(Root<AuctionItems> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(key), item);
            }
        };
    }
}
