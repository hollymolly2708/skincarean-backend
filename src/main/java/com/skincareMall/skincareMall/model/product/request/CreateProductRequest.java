package com.skincareMall.skincareMall.model.product.request;

import jakarta.validation.constraints.*;
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
public class CreateProductRequest {
    @Size(max = 100)
    @NotBlank
    private String productName;
    @Size(max = 100)
    @NotBlank
    private String productDescription;
    @Size(max = 255)
    @NotBlank
    private String thumbnailImage;
    private Boolean isPromo;
    @NotNull
    private Long brandId;
    @NotNull
    private Long categoryId;
    @Size(max = 100)
    @NotBlank
    private String bpomCode;
    @Size(max = 100)
    @NotBlank
    private String size;
    @Digits(integer = 2, fraction = 0, message = "Discount harus berupa angka dengan maksimal 2 digit dan 0 desimal")
    private BigDecimal discount = BigDecimal.ZERO;
    @NotNull
    @Digits(integer = 20, fraction = 2, message = "Harga harus berupa angka dengan maksimal 20 digit dan 2 desimal")
    @DecimalMin(value = "0.0", inclusive = false, message = "Harga harus lebih besar dari 0.0")
    private BigDecimal originalPrice;
    private Long stok;
    @NotNull
    private List<CreateProductImageRequest> productImages;

}
