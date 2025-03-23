package com.example.securityexam.securityex4.service;

import com.example.securityexam.securityex4.domain.Role;
import com.example.securityexam.securityex4.domain.User;
import com.example.securityexam.securityex4.repository.RoleRepository;
import com.example.securityexam.securityex4.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public User registUser(User user) {

        log.info("DB 저장 중: {}", user.getUsername());
        // 롤 정보를 User 엔티티에 채워줌


        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new IllegalArgumentException("USER 역할이 없습니다."));
        user.setRoles(Collections.singleton(userRole));  // 기본적으로 USER 역할만 부여

        // 패스워드는 반드시 암호화해서 넣어준다.
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("DB 저장 완료: {}", user.getUsername());

        return userRepository.save(user);
    }

    // DTO를 받아서 회원가입
//    public User registUser(UserRegiserDTO regiserDTO) {
//        String encodedPassword = passwordEncoder.encode(regiserDTO.getPassword());
//
//        Set<Role> roles = regiserDTO.getRoles().stream()
//                .map(roleRepository::findByName)
//                .flatMap(Optional::stream)  // Optional이 비어있다면 무시
//                .collect(Collectors.toSet());
//
//        User user = new User();
//        user.setUsername(regiserDTO.getUsername());
//        user.setPassword(encodedPassword);  // 인코딩한 password
//        user.setName(regiserDTO.getName());
//        user.setEmail(regiserDTO.getEmail());
//        user.setRoles(roles);  // roles
//
//        return userRepository.save(user);
//    }

    // username에 해당하는 사용자가 있는지 체크
    public boolean existsUser(String username) {
        return userRepository.existsByUsername(username);
    }
}
