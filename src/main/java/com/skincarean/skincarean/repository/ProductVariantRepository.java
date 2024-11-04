package com.skincarean.skincarean.repository;

import com.skincarean.skincarean.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant>findByIdAndProductId(Long id, String productId);
    List<ProductVariant> findAllByProductId(String productId);
}
