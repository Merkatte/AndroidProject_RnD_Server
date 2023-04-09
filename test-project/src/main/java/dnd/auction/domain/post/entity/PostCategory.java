package dnd.auction.domain.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Getter
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(max=20)
    @Column(nullable = false)
    String name;

    @Builder
    public PostCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
