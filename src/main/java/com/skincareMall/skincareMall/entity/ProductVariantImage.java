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
@Table(name = "product_variant_images")
public class ProductVariantImage {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productVariantImageId;
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(
            name = "product_variants_id",
            referencedColumnName = "id",
            nullable = false
    )
    private productVariant productVariant;
}
