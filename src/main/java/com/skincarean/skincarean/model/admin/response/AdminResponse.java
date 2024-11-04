package com.skincarean.skincarean.model.admin.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponse {
    private String username;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}
