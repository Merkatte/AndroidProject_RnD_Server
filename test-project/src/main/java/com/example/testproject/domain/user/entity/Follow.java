package com.example.testproject.domain.user.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long userId;

    @Column(nullable = false)
    Long followId;

    @PrePersist
    public void setDefault(){
        this.followedAt = LocalDateTime.now();
    }

    @Column(nullable = false)
    LocalDateTime followedAt;


    @Column(nullable = true)
    boolean isChecked;
}
