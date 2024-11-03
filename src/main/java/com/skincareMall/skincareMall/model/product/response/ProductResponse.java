package com.skincareMall.skincareMall.model.product.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ProductResponse {
    private String productId;
    private String productName;
    private Boolean isPromo;
    private String thumbnailImage;
    private Boolean isPopularProduct;
    private String brandName;
    private String categoryName;
    private BigDecimal firstOriginalPrice;
    private BigDecimal firstPrice;
    private BigDecimal firstDiscount;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

}
