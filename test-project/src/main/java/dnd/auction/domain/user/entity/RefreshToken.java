package dnd.auction.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshToken {
    @Id
    String refreshToken;

    @Column
    LocalDateTime createdAt;

    @PrePersist
    public void setDefault(){
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
