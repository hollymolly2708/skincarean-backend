package com.skincarean.skincarean.model.product_review.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductReviewRequest {
    @Min(1)
    @Max(5)
    private Byte rating;
    @NotBlank
    @Size(min = 200)
    private String review;
    @NotBlank
    @Size(max = 100)
    private String usagePeriod;
    private Boolean isRecommended;
}
