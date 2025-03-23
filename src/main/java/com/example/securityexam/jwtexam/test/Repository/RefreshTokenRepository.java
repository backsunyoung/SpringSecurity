package com.example.securityexam.jwtexam.test.Repository;

import com.example.securityexam.jwtexam.test.Domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByValue(String value);
}
