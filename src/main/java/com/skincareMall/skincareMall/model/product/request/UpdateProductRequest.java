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
public class UpdateProductRequest {
    @Size(max = 100)
    private String productName;
    @Size(max = 100)
    private String productDescription;
    @Size(max = 255)
    private String thumbnailImage;
    private Boolean isPromo;
    @Size(max = 100)
    private String brands;
    @Size(max = 100)
    private String category;
    @Size(max = 100)
    private String bpomCode;
    @Size(max = 100)
    private String size;
    @Digits(integer = 20, fraction = 2, message = "Harga harus berupa angka dengan maksimal 20 digit dan 2 desimal")
    @DecimalMin(value = "0.0", inclusive = false, message = "Harga harus lebih besar dari 0.0")
    private BigDecimal price;
    @Digits(integer = 2, fraction = 0, message = "Discount harus berupa angka dengan maksimal 2 digit dan 0 desimal")
    @DecimalMin(value = "0.0", inclusive = false, message = "Harga harus lebih besar dari 0.0")
    private BigDecimal discount;
    @Digits(integer = 20, fraction = 2, message = "Harga harus berupa angka dengan maksimal 20 digit dan 2 desimal")
    @DecimalMin(value = "0.0", inclusive = false, message = "Harga harus lebih besar dari 0.0")
    private BigDecimal originalPrice;
    private Long quantity;
    private List<UpdateProductImageRequest> productImages;

}