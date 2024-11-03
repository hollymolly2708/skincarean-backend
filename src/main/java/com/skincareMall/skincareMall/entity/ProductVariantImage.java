package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "product_variant_images")
public class ProductVariantImage {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_url")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(
            name = "product_variant_id",
            referencedColumnName = "id",
            nullable = false
    )
    private ProductVariant productVariant;
}
