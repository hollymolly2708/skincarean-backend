package com.skincareMall.skincareMall.model.cart.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long id;
    private Long quantity;
    private BigDecimal totalPrice;
    private List<CartItemResponse> cartItems;
}
