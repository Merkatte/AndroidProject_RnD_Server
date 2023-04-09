package dnd.auction.domain.post.specification;

public class FilterKeywordDTO {

    String username;
    String categoryName;
    String contents;

    public FilterKeywordDTO(String username, String categoryName, String contents) {
        this.username = username;
        this.categoryName = categoryName;
        this.contents = contents;
    }
}