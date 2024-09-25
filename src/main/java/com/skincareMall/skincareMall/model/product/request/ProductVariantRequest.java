package com.skincareMall.skincareMall.model.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantRequest {
    @Size(max = 100)
    @NotBlank
    private String size;
    @Size(max = 100)
    @NotBlank
    private BigDecimal price;
    private BigDecimal discount;
    @NotBlank
    private BigDecimal originalPrice;
    @Size(max = 100)
    @NotBlank
    private Long quantity;
    private List<ProductVariantImageRequest> productVariantImages;

}
