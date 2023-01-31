package com.example.testproject.domain.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data // Create getters and setters
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 20, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 8, message = "Minimum password length: 8 characters")
    @Column(nullable = false)
    private String password;

    @Size(max = 30)
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private LocalDate birth;

    @Size(max = 20)
    @Column(nullable = false)
    private String phoneNumber;

    @PrePersist
    public void setDefault(){
        this.createdAt = LocalDateTime.now();
        this.emailVerified = false;
        this.phoneNumberVerified = false;
    }
    private Boolean emailVerified;

    private Boolean phoneNumberVerified;

    private LocalDateTime createdAt;

    @ColumnDefault("null")
    private LocalDateTime deletedAt;

    @ColumnDefault("null")
    private LocalDateTime lastLogin;

    @ElementCollection(fetch = FetchType.EAGER)
    List<AppUserRole> appUserRoles;

}
