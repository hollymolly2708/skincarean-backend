package com.skincareMall.skincareMall.model.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {
    @Size(max = 100)
    @NotBlank
    private String productName;
    @Size(max = 100)
    @NotBlank
    private String productDescription;
    @Size(max = 100)
    @NotBlank
    private String thumbnailImage;
    private Boolean isPromo;
    @Size(max = 100)
    @NotBlank
    private String bpomCode;
    @NotNull
    private List<ProductVariantRequest> productVariants;

}
