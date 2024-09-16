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
@Table(name = "admins")
public class Admin {
    @Id
    private String username;
    private String password;
    @Column(name = "full_name")
    private String fullName;
    private String address;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    private String email;
    private String token;
    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;
    @Column(name = "token_created_at")
    private Long tokenCreatedAt;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;
    @OneToMany(mappedBy = "admin")
    private List<Product> products;
}
