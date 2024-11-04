package com.skincarean.skincarean.service.product.review;

import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.product_review.request.CreateProductReviewRequest;
import com.skincarean.skincarean.model.product_review.request.SearchProductReviewRequest;
import com.skincarean.skincarean.model.product_review.request.UpdateProductReviewRequest;
import com.skincarean.skincarean.model.product_review.response.ProductReviewResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductReviewService {

    void postProductReview(User user, String productId, CreateProductReviewRequest createProductReviewRequest);

    void deleteProductReview(User user, String productId, Long productReviewId);

    void updateProductReview(User user, String productId, Long productReviewId, UpdateProductReviewRequest updateProductReviewRequest);

    List<ProductReviewResponse> getAllReviews(String productId);

    Page<ProductReviewResponse> searchReview(SearchProductReviewRequest searchProductReviewRequest);
}
