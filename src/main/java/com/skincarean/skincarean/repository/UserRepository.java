package com.skincarean.skincarean.repository;

import com.skincarean.skincarean.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findFirstByToken(String token);
    Optional<User> findByEmail(String email);

}
