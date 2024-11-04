package com.skincarean.skincarean.repository;

import com.skincarean.skincarean.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByIsPopularProduct(Boolean isPopularProduct);
    List<Product> findByBrandId(Long brandId);
}
