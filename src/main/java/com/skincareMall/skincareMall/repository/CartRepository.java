package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserUsernameUserAndId(String username, Long id);
    Optional<Cart> findByUserUsernameUserAndProductId(String username, String productId);
    List<Cart> findByUserUsernameUser(String username);

}
