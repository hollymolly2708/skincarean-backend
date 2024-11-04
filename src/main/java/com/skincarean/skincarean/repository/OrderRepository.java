package com.skincarean.skincarean.repository;

import com.skincarean.skincarean.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserUsernameUser(String userId);

    List<Order> findByUserUsernameUserAndOrderStatus(String userId, String orderStatus);
}
