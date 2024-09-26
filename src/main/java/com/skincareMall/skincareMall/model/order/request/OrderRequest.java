package com.skincareMall.skincareMall.model.order.request;

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
public class OrderRequest {
    @Size(max = 100)
    @NotBlank
    private String paymentStatus;
    @NotBlank
    @Size(max = 255)
    private String shippingAddress;
    @NotNull
    @Digits(integer = 20, fraction = 2, message = "Harga harus berupa angka dengan maksimal 20 digit dan 2 desimal")
    private BigDecimal shippingCost= BigDecimal.valueOf(20000.00);
    @NotNull
    @Digits(integer = 20, fraction = 2, message = "Harga harus berupa angka dengan maksimal 20 digit dan 2 desimal")
    private BigDecimal tax = BigDecimal.valueOf(10000.00);
    @NotNull
    private Long paymentMethodId;
    @NotNull
    private Long quantity;
    @Size(max = 255)
    private String description;
    @NotNull
    private String productId;

}
