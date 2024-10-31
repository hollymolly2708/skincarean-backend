package com.skincareMall.skincareMall.model.product.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductVariantRequest {
    private Long id;
    private String size;
    private Long stok;
    @Digits(integer = 2, fraction = 0, message = "Discount harus berupa angka dengan maksimal 2 digit dan 0 desimal")
    private BigDecimal discount;
    @Digits(integer = 20, fraction = 2, message = "Harga harus berupa angka dengan maksimal 20 digit dan 2 desimal")
    @DecimalMin(value = "0.0", inclusive = false, message = "Harga harus lebih besar dari 0.0")
    private BigDecimal originalPrice;

}
