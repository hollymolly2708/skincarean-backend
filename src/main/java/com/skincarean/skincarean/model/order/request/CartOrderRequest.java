package com.skincarean.skincarean.model.order.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartOrderRequest {
    @NotBlank
    @Size(max = 255)
    private String shippingAddress;
    @NotNull
    private Long paymentMethodId;
    @Size(max = 255)
    private String description;


}
