package dnd.auction.domain.post.dto;

import lombok.Builder;

@Builder
public record PostDTO(
        Long userId,
        String title,
        String contents,
        String category
) {
}
