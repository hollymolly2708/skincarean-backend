package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.Cart;
import com.skincareMall.skincareMall.entity.CartItem;
import com.skincareMall.skincareMall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//    Optional<CartItem> findByUserUsernameUserAndId(String username, Long id);
//    Optional<CartItem> findByUserUsernameUserAndProductId(String username, String productId);
//    List<CartItem> findByUserUsernameUser(String username);

    Optional<CartItem> findByProductId(String productId);

    Optional<CartItem> findById(Long cartId);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findByCartId(Long cartId);

}
