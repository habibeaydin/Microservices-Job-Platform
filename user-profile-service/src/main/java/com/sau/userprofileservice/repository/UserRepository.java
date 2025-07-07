package com.sau.userprofileservice.repository;

import com.sau.userprofileservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAuthUserId(Long authUserId);
    Optional<User> findById(Long id);
    boolean existsByAuthUserId(Long authUserId);
}
