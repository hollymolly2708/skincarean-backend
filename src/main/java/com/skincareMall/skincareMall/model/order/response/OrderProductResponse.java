package com.skincareMall.skincareMall.model.order.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductResponse {
    private String productId;
    private String productName;
    private String thumbnailImage;
    private String brandName;
    private String categoryName;

}
