package com.example.testproject.domain.post.entity;

import com.example.testproject.domain.user.entity.AppUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @Size(max = 100)
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDate createdDate;

    @OneToMany(mappedBy = "post")
    List<Comment> comments = new ArrayList<>();

    @PrePersist
    private void setDefault(){
        this.createdAt = LocalDateTime.now();
        this.createdDate = LocalDate.now();
    }

    @Builder
    public Post(Long id, AppUser appUser, String title, String contents, LocalDateTime createdAt, LocalDate createdDate) {
        this.id = id;
        this.appUser = appUser;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.createdDate = createdDate;
    }
}
