package com.skincareMall.skincareMall.entity;


import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Not;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    private String password;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    private String token;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;
    @Column(name = "token_expired_at")
    private String tokenExpiredAt;
    private String phone;
}
