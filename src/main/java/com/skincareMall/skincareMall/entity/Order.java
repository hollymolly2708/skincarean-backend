package com.skincareMall.skincareMall.entity;

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
@Table(name = "orders")
public class Order {
    @Id
    private String id;
    @Column(name = "order_date")
    private Timestamp orderDate;
    private String status;
    private BigDecimal total;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    private User user;
}
