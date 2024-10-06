package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "brands")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "brand_logo")
    private String brandLogo;
    @Column(name = "brand_poster")
    private String brandPoster;
    @Column(name = "website_media_url")
    private String websiteMediaUrl;
    @Column(name = "instagram_url")
    private String instagramUrl;
    @Column(name = "facebook_url")
    private String facebookUrl;
    @Column(name = "contact_email_url")
    private String contactEmailUrl;
    private String address;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;
    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
