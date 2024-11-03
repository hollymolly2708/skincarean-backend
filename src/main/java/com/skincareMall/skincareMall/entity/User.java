package com.skincareMall.skincareMall.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username")
    private String usernameUser;
    @Column(name = "password")
    private String passwordUser;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    private String token;
    private String address;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "photo_profile")
    private String photoProfile;
    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;
    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;
    @Column(name = "token_created_at")
    private Long tokenCreatedAt;
    private String phone;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> productReviews;
    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Cart cart;
}
