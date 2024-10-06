package com.skincareMall.skincareMall.service.product.review;

import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.ProductReview;
import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.product_review.request.CreateProductReviewRequest;
import com.skincareMall.skincareMall.model.product_review.request.SearchProductReviewRequest;
import com.skincareMall.skincareMall.model.product_review.request.UpdateProductReviewRequest;
import com.skincareMall.skincareMall.model.product_review.response.ProductReviewResponse;
import com.skincareMall.skincareMall.repository.ProductRepository;
import com.skincareMall.skincareMall.repository.ProductReviewRepository;
import com.skincareMall.skincareMall.repository.UserRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductReviewService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void postProductReview(User user, String productId, CreateProductReviewRequest createProductReviewRequest) {
        validationService.validate(createProductReviewRequest);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));
        ProductReview productReview = new ProductReview();
        productReview.setProduct(product);
        productReview.setReview(createProductReviewRequest.getReview());
        productReview.setCreatedAt(Utilities.changeFormatToTimeStamp());
        productReview.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
        productReview.setUser(user);
        productReview.setRating(createProductReviewRequest.getRating());
        productReview.setIsRecommended(createProductReviewRequest.getIsRecommended());
        productReview.setUsagePeriod(createProductReviewRequest.getUsagePeriod());
        productReviewRepository.save(productReview);
    }

    @Transactional
    public void deleteProductReview(User user, String productId, Long productReviewId) {
        ProductReview productReview = productReviewRepository.findByIdAndProductIdAndUserUsernameUser(productReviewId, productId, user.getUsernameUser()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));
        productReviewRepository.deleteById(productReview.getId());
    }

    @Transactional
    public void updateProductReview(User user, String productId, Long productReviewId, UpdateProductReviewRequest updateProductReviewRequest) {
        validationService.validate(updateProductReviewRequest);
        ProductReview productReview = productReviewRepository.findByIdAndProductIdAndUserUsernameUser(productReviewId, productId, user.getUsernameUser()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));

        if (Objects.nonNull(updateProductReviewRequest.getReview())) {
            productReview.setReview(updateProductReviewRequest.getReview());
        }

        if (Objects.nonNull(updateProductReviewRequest.getUsagePeriod())) {
            productReview.setUsagePeriod(updateProductReviewRequest.getUsagePeriod());
        }
        if (Objects.nonNull(updateProductReviewRequest.getIsRecommended())) {
            productReview.setIsRecommended(updateProductReviewRequest.getIsRecommended());
        }
        if (Objects.nonNull(updateProductReviewRequest.getRating())) {
            productReview.setRating(updateProductReviewRequest.getRating());
        }
        productReview.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
        productReviewRepository.save(productReview);
    }

    @Transactional(readOnly = true)
    public List<ProductReviewResponse> getAllReviews(String productId) {
        List<ProductReview> productReviewByProductId = productReviewRepository.findByProductId(productId);
        List<ProductReviewResponse> productReviewResponses = productReviewByProductId.stream().map(productReview -> {
            return ProductReviewResponse.builder()
                    .fullNameUser(productReview.getUser().getFullName())
                    .review(productReview.getReview())
                    .createdAt(productReview.getCreatedAt())
                    .usagePeriod(productReview.getUsagePeriod())
                    .photoProfileUser(productReview.getUser().getPhotoProfile())
                    .rating(productReview.getRating())
                    .isRecommended(productReview.getIsRecommended())
                    .build();
        }).toList();

        return productReviewResponses;
    }

    @Transactional(readOnly = true)
    public Page<ProductReviewResponse> searchReview(SearchProductReviewRequest searchProductReviewRequest) {
        Specification<ProductReview> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(searchProductReviewRequest.getIsRecommended())) {
                predicates.add(criteriaBuilder.equal(root.get("isRecommended"), searchProductReviewRequest.getIsRecommended()));
            }
            if (Objects.nonNull(searchProductReviewRequest.getRating())) {
                predicates.add(criteriaBuilder.equal(root.get("rating"),  searchProductReviewRequest.getRating()));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(searchProductReviewRequest.getPage(), searchProductReviewRequest.getSize());
        Page<ProductReview> productReviews = productReviewRepository.findAll(specification, pageable);
        List<ProductReviewResponse> productReviewResponses = productReviews.getContent().stream().map(productReview -> ProductReviewResponse.builder()
                .fullNameUser(productReview.getUser().getFullName())
                .photoProfileUser(productReview.getUser().getPhotoProfile())
                .usagePeriod(productReview.getUsagePeriod())
                .isRecommended(productReview.getIsRecommended())
                .createdAt(productReview.getCreatedAt())
                .rating(productReview.getRating())
                .review(productReview.getReview())
                .build()).toList();
        return new PageImpl<>(productReviewResponses, pageable, productReviews.getTotalElements());
    }

}
