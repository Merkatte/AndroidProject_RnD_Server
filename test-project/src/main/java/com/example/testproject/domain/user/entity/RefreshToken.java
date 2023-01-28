package com.example.testproject.domain.user.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RefreshToken {
    @Id
    String refreshToken;
}
