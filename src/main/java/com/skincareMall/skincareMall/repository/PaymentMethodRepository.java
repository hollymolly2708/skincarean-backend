package com.skincareMall.skincareMall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<com.skincareMall.skincareMall.entity.PaymentMethod, Long> {
}
