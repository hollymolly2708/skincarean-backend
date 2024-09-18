package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @Column(name = "id")
    private String productImageId;
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(
            name = "product_variant_id",
            referencedColumnName = "id",
            nullable = false
    )
    private ProductCategory productCategory;
}
