package com.skincareMall.skincareMall.model.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {
    private String productName;
    private String productDescription;
    private Boolean isPromo;
    private String thumbnailImage;
    private String bpomCode;
    private List<ProductCategoryResponse> productCategoryList;
}
