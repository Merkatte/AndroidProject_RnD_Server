package dnd.auction.domain.post.entity;

import dnd.auction.domain.user.entity.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @Column(nullable = false)
    private String contents;

    private LocalDateTime createdAt;

    @Builder
    public Comment(Long id, Post post, AppUser appUser, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.post = post;
        this.appUser = appUser;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    @PrePersist
    private void setDefault(){
        this.createdAt = LocalDateTime.now();
    }
}
