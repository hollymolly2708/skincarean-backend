package com.skincareMall.skincareMall.model.cart.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCartRequest {
    @Size(max = 255)
    @NotBlank
    private String productId;
    @NotNull
    private Long quantity;
    @NotNull
    private Long productVariantId;
}
