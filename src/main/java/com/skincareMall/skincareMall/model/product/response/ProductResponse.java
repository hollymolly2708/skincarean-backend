package com.skincareMall.skincareMall.model.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String productName;
    private String productDescription;
    private String addedByAdmin;
    private String thumbnailImage;
    private Boolean isPromo;
}
