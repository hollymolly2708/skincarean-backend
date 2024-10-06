package com.skincareMall.skincareMall.model.product_review.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductReviewRequest {
    private Byte rating;
    private Boolean isRecommended;
    private Integer page;
    private Integer size;
}
