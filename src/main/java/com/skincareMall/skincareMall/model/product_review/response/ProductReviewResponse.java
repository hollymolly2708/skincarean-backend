package com.skincareMall.skincareMall.model.product_review.response;

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
    //user didapatkan dari table product_review dengan column user_id
    private String fullNameUser;
    private String photoProfileUser;
    private Long reviewId;

    //review diambil dari table product_review
    private String review;
    private Boolean isRecommended;
    private Byte rating;
    private String usagePeriod;
    private Timestamp createdAt;
}
