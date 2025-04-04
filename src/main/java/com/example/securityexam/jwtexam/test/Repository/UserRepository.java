package com.example.securityexam.jwtexam.test.Repository;

import com.example.securityexam.jwtexam.test.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}