package com.skincarean.skincarean.repository;

import com.skincarean.skincarean.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Boolean existsByName(String name);
}
