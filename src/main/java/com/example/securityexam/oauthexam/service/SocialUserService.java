package com.example.securityexam.oauthexam.service;


import com.example.securityexam.oauthexam.entity.SocialUser;
import com.example.securityexam.oauthexam.repository.SocialUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialUserService {

    private final SocialUserRepository socialUserRepository;

    // 소셜에서 보내준 정보를 저장하기 위한 메서드


    @Transactional
    public SocialUser saveOrUpdateUser(String socialId, String provider, String username, String email, String avatarUrl){
        Optional<SocialUser> existingUser = socialUserRepository.findBySocialIdAndProvider(socialId, provider);
        SocialUser socialUser;
        if(existingUser.isPresent()){
            // 이미 소셜 정보를 가진 상용자라면
            socialUser = existingUser.get();
            socialUser.setSocialId(socialId);
            socialUser.setEmail(email);
            socialUser.setAvatarUrl(avatarUrl);
        } else {

            // 처음 방문
            socialUser = new SocialUser();
            socialUser.setSocialId(socialId);
            socialUser.setUsername(username);
            socialUser.setEmail(email);
            socialUser.setAvatarUrl(avatarUrl);
            socialUser.setProvider(provider);
        }

        return socialUserRepository.save(socialUser);
    }
}
