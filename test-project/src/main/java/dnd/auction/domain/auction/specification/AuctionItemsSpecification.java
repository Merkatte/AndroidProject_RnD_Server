package dnd.auction.domain.auction.specification;

import dnd.auction.domain.auction.entity.Item;
import dnd.auction.domain.auction.entity.AuctionItems;
import dnd.auction.domain.auction.entity.ItemParts;
import dnd.auction.domain.auction.entity.ItemType;
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

    public static Specification<AuctionItems> equalItemType(String option, ItemType itemType){
        return new Specification<AuctionItems>() {
            @Override
            public Predicate toPredicate(Root<AuctionItems> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<AuctionItems, Item> categorizedItem = root.join("item");
                return criteriaBuilder.equal(categorizedItem.get(option), itemType);
            }
        };
    }

    public static Specification<AuctionItems> equalItemParts(String option, ItemParts itemParts){
        return new Specification<AuctionItems>() {
            @Override
            public Predicate toPredicate(Root<AuctionItems> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<AuctionItems, Item> categorizedItem = root.join("item");
                return criteriaBuilder.equal(categorizedItem.get(option), itemParts);
            }
        };
    }

    public static Specification<AuctionItems> equalItem(String key, Item item){
        return new Specification<AuctionItems>() {
            @Override
            public Predicate toPredicate(Root<AuctionItems> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(key), item);
            }
        };
    }

    public static Specification<AuctionItems> filterCompleted(String completed, boolean isCompleted){
        return new Specification<AuctionItems>() {
            @Override
            public Predicate toPredicate(Root<AuctionItems> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(completed), isCompleted);
            }
        };
    }

}
