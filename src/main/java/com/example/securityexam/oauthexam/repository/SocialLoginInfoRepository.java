package com.example.securityexam.oauthexam.repository;

import com.example.securityexam.oauthexam.entity.SocialLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialLoginInfoRepository extends JpaRepository<SocialLoginInfo, Long> {

    Optional<SocialLoginInfo> findByProviderAndUuidAndSocialId(String provider, String uuid, String socialId);

}
