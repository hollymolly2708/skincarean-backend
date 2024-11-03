package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserUsernameUser(String username);
}
