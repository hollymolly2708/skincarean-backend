package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(OrderItemId.class)
public class OrderItem {
    @Id
    private String orderId;
    @Id
    private String productId;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal total;

    public BigDecimal getTotal() {
       return price.subtract(price.multiply(
                discount.divide(BigDecimal.valueOf(100)))
                .setScale(2, BigDecimal.ROUND_HALF_UP))
                .multiply(BigDecimal.valueOf(quantity));
    }
}
