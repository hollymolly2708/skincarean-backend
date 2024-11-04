package com.skincarean.skincarean.repository;

import com.skincarean.skincarean.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>, JpaSpecificationExecutor<ProductReview> {
    Optional<ProductReview> findByIdAndProductIdAndUserUsernameUser(Long id, String productId, String username);
    List<ProductReview> findByProductId(String productId);
}
