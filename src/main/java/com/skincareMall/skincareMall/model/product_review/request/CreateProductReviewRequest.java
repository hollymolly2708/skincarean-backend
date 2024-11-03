package com.skincareMall.skincareMall.model.product_review.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductReviewRequest {
    @Min(1)
    @Max(5)
    @NotNull
    private Byte rating;
    @NotBlank
    private String review;
    @NotBlank
    @Size(max = 100)
    private String usagePeriod;
    @NotNull
    private Boolean isRecommended;
}
