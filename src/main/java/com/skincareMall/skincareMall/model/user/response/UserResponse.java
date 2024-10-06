package com.skincareMall.skincareMall.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private String fullName;
    private String address;
    private String email;
    private String phone;
    private String profilePicture;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
    private String token;
    private Timestamp tokenCreatedAt;
    private Timestamp tokenExpiredAt;
}
