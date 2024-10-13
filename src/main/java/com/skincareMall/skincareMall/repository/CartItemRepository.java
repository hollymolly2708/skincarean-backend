package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByProductId(String productId);

    List<CartItem> findAllByCartId(Long cartId);
}
