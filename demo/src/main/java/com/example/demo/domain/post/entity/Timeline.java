package com.example.demo.domain.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    Long userId;

    @JoinColumn(name="post_id")
    @ManyToOne
    Post post;

    @Builder
    public Timeline(Long id, Long userId, Post post) {
        this.id = id;
        this.userId = userId;
        this.post = post;
    }
}
