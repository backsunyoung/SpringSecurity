package com.example.securityexam.oauthexam.service;

import com.example.securityexam.oauthexam.dto.SocialUserRequestDto;
import com.example.securityexam.oauthexam.entity.Role;
import com.example.securityexam.oauthexam.entity.User;
import com.example.securityexam.oauthexam.repository.RoleRepository;
import com.example.securityexam.oauthexam.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //private final PasswordEncoder passwordEncoder;


    @Transactional
    public User saveUser(SocialUserRequestDto requestDto, PasswordEncoder passwordEncoder){
        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setSocialId(requestDto.getSocialId());
        user.setProvider(requestDto.getProvider());
        user.setPassword(passwordEncoder.encode("")); // 소셜로그인으로 진행하는 사용자느 는 비밀번호를 비워둔다/

        Role userRole = roleRepository.findByName("user2").orElseThrow();
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
//롤테이블에 데이터가 없었다?
    }


    @Transactional//(readOnly = true)
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public Optional<User> findByProviderAndSocialId(String provider, String socialId){
        return userRepository.findByProviderAndSocialId(provider, socialId);
    }
}
