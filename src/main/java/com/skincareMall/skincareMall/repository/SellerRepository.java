package com.skincareMall.skincareMall.repository;

import com.skincareMall.skincareMall.entity.Seller;
import com.skincareMall.skincareMall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findSellerByUser(User user);
}
