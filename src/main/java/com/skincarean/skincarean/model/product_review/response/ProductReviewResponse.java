package com.skincarean.skincarean.model.product_review.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReviewResponse {
    private String fullNameUser;
    private String photoProfileUser;
    private Long reviewId;
    private String review;
    private Boolean isRecommended;
    private Byte rating;
    private String usagePeriod;
    private Timestamp createdAt;
}
