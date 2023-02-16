package com.example.demo.domain.follow.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column
    Long userId;

    @Column
    Long followId;

    @Builder
    public Follow(Long id, Long userId, Long followId) {
        this.id = id;
        this.userId = userId;
        this.followId = followId;
    }
}
