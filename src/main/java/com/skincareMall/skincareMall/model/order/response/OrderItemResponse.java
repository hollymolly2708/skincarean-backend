package com.skincareMall.skincareMall.model.order.response;

import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.model.payment_process.response.PaymentProcessResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private String id;
    private BigDecimal price;
    private Long quantity;
    private Timestamp createdAt;
    private Timestamp expiredAt;
    private OrderProductResponse product;
    private OrderProductVariantResponse productVariant;


}
