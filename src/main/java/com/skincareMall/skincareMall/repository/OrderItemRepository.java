package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.OrderItem;
import com.skincareMall.skincareMall.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
