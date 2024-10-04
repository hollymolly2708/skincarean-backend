package com.skincareMall.skincareMall.model.order.response;

import com.skincareMall.skincareMall.entity.PaymentProcess;
import com.skincareMall.skincareMall.model.payment_process.response.PaymentProcessResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponse {
    private String orderId;
    private String productId;
    private String shippingAddress;
    private Long quantity;
    private String description;
    private BigDecimal shippingCost;
    private BigDecimal tax;
    private BigDecimal totalPrice;
    private Timestamp createdAt;
    private Timestamp expiredAt;
    private ProductResponse product;
    private PaymentProcessResponse payment;
}
