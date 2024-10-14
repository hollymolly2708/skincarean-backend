package com.skincareMall.skincareMall.model.order.response;

import com.skincareMall.skincareMall.entity.OrderItem;
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

    private String orderStatus;
    private BigDecimal finalPrice;
    private String description;
    private BigDecimal shippingCost;
    private String shippingAddress;
    private BigDecimal tax;
    private List<OrderItemResponse> orderItems;
    private PaymentProcessResponse payment;
}
