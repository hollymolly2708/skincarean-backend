package com.skincarean.skincarean.repository;

import com.skincarean.skincarean.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    OrderItem findByOrderId(String orderId);
}
