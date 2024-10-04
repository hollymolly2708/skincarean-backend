package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String id;
    private Long quantity;
    private String description;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "shipping_cost")
    private BigDecimal shippingCost;
    private BigDecimal tax;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "expired_at")
    private Timestamp expiredAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentProcess paymentProcess;


}
