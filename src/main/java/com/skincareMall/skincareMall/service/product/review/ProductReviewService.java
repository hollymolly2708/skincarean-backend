package com.skincareMall.skincareMall.service.product.review;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.product_review.request.CreateProductReviewRequest;
import com.skincareMall.skincareMall.model.product_review.request.SearchProductReviewRequest;
import com.skincareMall.skincareMall.model.product_review.request.UpdateProductReviewRequest;
import com.skincareMall.skincareMall.model.product_review.response.ProductReviewResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductReviewService {

    void postProductReview(User user, String productId, CreateProductReviewRequest createProductReviewRequest);

    void deleteProductReview(User user, String productId, Long productReviewId);

    void updateProductReview(User user, String productId, Long productReviewId, UpdateProductReviewRequest updateProductReviewRequest);

    List<ProductReviewResponse> getAllReviews(String productId);

    Page<ProductReviewResponse> searchReview(SearchProductReviewRequest searchProductReviewRequest);
}
