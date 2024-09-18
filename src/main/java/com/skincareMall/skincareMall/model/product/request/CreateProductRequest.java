package com.skincareMall.skincareMall.model.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductRequest {
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

}
