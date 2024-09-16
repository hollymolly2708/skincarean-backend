package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    @Column(name = "thumbnail_image")
    private String thumbnailImage;
    @Column(name = "bpom_code")
    private String bpomCode;
    @Column(name = "is_promo")
    private Boolean isPromo;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Admin admin;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;
}
