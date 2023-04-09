package dnd.auction.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String path;

    @Builder
    public Image(Long id, Post post, String name, String path) {
        this.id = id;
        this.post = post;
        this.name = name;
        this.path = path;
    }
}
