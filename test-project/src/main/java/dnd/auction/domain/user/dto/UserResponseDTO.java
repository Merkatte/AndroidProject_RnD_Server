package dnd.auction.domain.user.dto;

import dnd.auction.domain.user.entity.AppUser;
import lombok.Getter;

@Getter
public class UserResponseDTO {
    Long id;
    String username;

    public UserResponseDTO(AppUser appUser){
        this.id = appUser.getId();
        this.username = appUser.getUsername();
    }
}
