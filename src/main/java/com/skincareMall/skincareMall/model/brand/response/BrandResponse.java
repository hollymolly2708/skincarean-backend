package com.skincareMall.skincareMall.model.brand.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private Long id;
    private String name;
    private String description;
    private String websiteMediaUrl;
    private String instagramUrl;
    private String facebookUrl;
    private String contactEmailUrl;
    private String brandPoster;
    private String brandLogo;
    private String address;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}
