package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.productVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<productVariant, String> {
}
