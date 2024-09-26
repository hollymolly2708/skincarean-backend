package com.skincareMall.skincareMall.model.product.response;

import com.skincareMall.skincareMall.entity.ProductImage;
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
public class DetailProductResponse {
    private String productId;
    private String productName;
    private String productDescription;
    private Boolean isPromo;
    private String thumbnailImage;
    private String bpomCode;
    private String size;
    private Long stok;
    private BigDecimal originalPrice;
    private BigDecimal price;
    private BigDecimal discount;
    private String brands;
    private String category;
    private List<ProductImageResponse> productImage;
}
