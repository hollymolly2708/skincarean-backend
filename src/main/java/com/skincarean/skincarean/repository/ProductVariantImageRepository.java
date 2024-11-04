package com.skincarean.skincarean.repository;

import com.skincarean.skincarean.entity.ProductVariantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantImageRepository extends JpaRepository<ProductVariantImage, Long> {
    List<ProductVariantImage> findAllByProductVariantId(Long productVariantId);
    Optional<ProductVariantImage> findByIdAndProductVariantId(Long id, Long productVariant);
}
