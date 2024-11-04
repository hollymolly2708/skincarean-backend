package com.skincarean.skincarean.controller.product.review;

import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.PagingResponse;
import com.skincarean.skincarean.model.product_review.request.CreateProductReviewRequest;
import com.skincarean.skincarean.model.product_review.request.SearchProductReviewRequest;
import com.skincarean.skincarean.model.product_review.request.UpdateProductReviewRequest;
import com.skincarean.skincarean.model.product_review.response.ProductReviewResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;
import com.skincarean.skincarean.service.product.review.ProductReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductReviewController {
    @Autowired
    private ProductReviewServiceImpl productReviewServiceImpl;

    @PostMapping(path = "/api/products/{productId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> postProductReview(User user, @PathVariable("productId") String productId, @RequestBody CreateProductReviewRequest createProductReviewRequest) {
        productReviewServiceImpl.postProductReview(user, productId, createProductReviewRequest);
        return WebResponse.<String>builder().data("Terima kasih, review anda telah ditambahkan").build();
    }

    @DeleteMapping(path = "/api/products/{productId}/reviews/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteProductReview(User user, @PathVariable("productId") String productId, @PathVariable("reviewId") Long reviewId) {
        productReviewServiceImpl.deleteProductReview(user, productId, reviewId);
        return WebResponse.<String>builder().data("Review berhasil dihapus").build();
    }

    @PatchMapping(path = "/api/products/{productId}/reviews/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> updateProductReview(User user, @PathVariable("productId") String productId, @PathVariable("reviewId") Long reviewId, @RequestBody UpdateProductReviewRequest updateProductReviewRequest) {
        productReviewServiceImpl.updateProductReview(user, productId, reviewId, updateProductReviewRequest);
        return WebResponse.<String>builder().data("Review anda berhasil diubah").build();
    }

    @GetMapping(path = "/api/products/{productId}/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProductReviewResponse>> getAllReviews(@PathVariable("productId") String productId) {
        List<ProductReviewResponse> allReviews = productReviewServiceImpl.getAllReviews(productId);
        return WebResponse.<List<ProductReviewResponse>>builder().data(allReviews).build();
    }

    @GetMapping(path = "/api/products/{productId}/reviews/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProductReviewResponse>> searchProduct(@RequestParam(name = "rating", required = false) Byte rating,
                                                                  @RequestParam(name = "isRecommended",required = false) Boolean isRecommended,
                                                                  @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                                  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page

    ) {
        SearchProductReviewRequest request = SearchProductReviewRequest.builder()
                .page(page)
                .isRecommended(isRecommended)
                .rating(rating)
                .size(size)
                .build();
        Page<ProductReviewResponse> productReviewResponses = productReviewServiceImpl.searchReview(request);
        return WebResponse.<List<ProductReviewResponse>>builder().data(productReviewResponses.getContent()).paging(PagingResponse.builder().currentPage(productReviewResponses.getNumber()).totalPage(productReviewResponses.getTotalPages()).size(productReviewResponses.getSize()).build()).build();

    }
}
