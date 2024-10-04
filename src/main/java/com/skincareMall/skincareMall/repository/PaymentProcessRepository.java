package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.PaymentProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentProcessRepository extends JpaRepository<PaymentProcess, Long> {
    PaymentProcess findByOrderId(String id);
    Optional<PaymentProcess> findByPaymentCode(String paymentCode);
}
