package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserUsernameUser(String userId);
}
