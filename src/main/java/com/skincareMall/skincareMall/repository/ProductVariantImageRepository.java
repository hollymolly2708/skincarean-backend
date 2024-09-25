package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.ProductVariantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantImageRepository extends JpaRepository<ProductVariantImage, String> {
}
