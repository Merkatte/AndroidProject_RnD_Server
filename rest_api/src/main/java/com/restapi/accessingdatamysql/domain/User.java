package com.restapi.accessingdatamysql.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    @Id
    private Long id;
    private String username;
    private String email;

    @Builder
    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}