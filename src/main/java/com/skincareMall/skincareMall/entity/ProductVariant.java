package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variants")
public class ProductVariant {
    @Id
    private String id;
    private String size;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discount;
    @Column(name = "original_price")
    private BigDecimal originalPrice;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

}
