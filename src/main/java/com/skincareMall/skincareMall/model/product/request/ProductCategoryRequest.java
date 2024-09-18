package com.skincareMall.skincareMall.model.product.request;

import jakarta.validation.constraints.NotBlank;
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
public class ProductCategoryRequest {
    @Size(max = 100)
    @NotBlank
    private String size;
    @Size(max = 100)
    @NotBlank
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal originalPrice;
    @Size(max = 100)
    @NotBlank
    private Long quantity;

}
