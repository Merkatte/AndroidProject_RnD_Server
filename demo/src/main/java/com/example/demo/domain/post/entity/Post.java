package com.example.demo.domain.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(nullable = false)
    String contents;

    @Column(nullable = false)
    Long userId;

    @Column
    LocalDateTime createdAt;

    @Builder
    public Post(Long id, String contents, Long userId, LocalDateTime createdAt) {
        this.id = id;
        this.contents = contents;
        this.userId = userId;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

}
