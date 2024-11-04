package com.skincarean.skincarean.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    private String id;

    private Long quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;


    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "expired_at")
    private Timestamp expiredAt;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_variant_id", referencedColumnName = "id")
    private ProductVariant productVariant;


}
