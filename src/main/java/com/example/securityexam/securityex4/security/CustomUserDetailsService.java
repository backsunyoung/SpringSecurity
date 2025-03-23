package com.example.securityexam.securityex4.security;

import com.example.securityexam.securityex4.domain.Role;
import com.example.securityexam.securityex4.domain.User;
import com.example.securityexam.securityex4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import org.springframework.security.core.userdetails.User.UserBuilder;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username +"에 해당하는 사용자가 없습니다.");
        }

        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(user.getPassword());

        userBuilder.roles(user.getRoles().stream().map(Role::getName).toList().toArray(new String[0]));

        // 권한 설정 (ROLE_ prefix가 포함되어야 함)
//        userBuilder.roles(user.getRoles().stream()
//                .map(role -> "ROLE_" + role.getName()) // "ROLE_"을 추가
//                .toArray(String[]::new)); // toArray()로 배열로 변환

        return userBuilder.build();
    }
}