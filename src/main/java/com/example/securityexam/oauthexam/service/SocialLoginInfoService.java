package com.example.securityexam.oauthexam.service;

import com.example.securityexam.oauthexam.entity.SocialLoginInfo;
import com.example.securityexam.oauthexam.entity.SocialUser;
import com.example.securityexam.oauthexam.repository.SocialLoginInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialLoginInfoService {

    private final SocialLoginInfoRepository socialLoginInfoRepository;

    @Transactional//(readOnly = true)
    public SocialLoginInfo saveSocialLoginInfo(String provider, String socialId){
        SocialLoginInfo socialLoginInfo = new SocialLoginInfo();
        socialLoginInfo.setProvider(provider);
        socialLoginInfo.setSocialId(socialId);
        return socialLoginInfoRepository.save(socialLoginInfo);

    }

    @Transactional//(readOnly = true)
    public Optional<SocialLoginInfo> findByProviderAndUuidAndSocialId(String provider, String uuid, String socialId){
        return socialLoginInfoRepository.findByProviderAndUuidAndSocialId(provider,uuid,socialId);
    }

}
