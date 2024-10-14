package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    OrderItem findByOrderId(String orderId);
}
