package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_categories")
public class ProductCategory {
    @Id
    @Column(name = "id")
    private String productVariantId;
    private String size;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal discount;
    @Column(name = "original_price")
    private BigDecimal originalPrice;
    @ManyToOne
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id",
            nullable = false)
    private Product product;
    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

}
