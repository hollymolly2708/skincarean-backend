package com.skincarean.skincarean.repository;

import com.skincarean.skincarean.entity.Cart;
import com.skincarean.skincarean.entity.CartItem;
import com.skincarean.skincarean.entity.Product;
import com.skincarean.skincarean.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    Optional<CartItem> findByProductId(String productId);

    List<CartItem> findAllByCart(Cart cart);

    Optional<CartItem> findById(Long cartId);

    Optional<CartItem> findByCartAndProductAndProductVariant(Cart cart, Product product, ProductVariant productVariant);

    List<CartItem> findByCartAndIsActive(Cart cart, Boolean isActive);


}
