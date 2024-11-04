package com.skincarean.skincarean.model.order.response;

import com.skincarean.skincarean.model.payment_process.response.PaymentProcessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
