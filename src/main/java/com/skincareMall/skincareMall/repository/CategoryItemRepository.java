package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {
    List<CategoryItem> findAllByCategoryId(Long id);
    Optional<CategoryItem> findByIdAndCategoryId(Long id, Long categoryId);
}
