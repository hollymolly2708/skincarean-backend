package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

}
