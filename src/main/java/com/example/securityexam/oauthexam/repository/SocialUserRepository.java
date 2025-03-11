package com.example.securityexam.oauthexam.repository;

import com.example.securityexam.oauthexam.entity.SocialUser;
import com.example.securityexam.oauthexam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
    Optional<SocialUser> findBySocialIdAndProvider(String socialId, String provider);
}