package com.example.securityexam.securityex4.config;


import com.example.securityexam.securityex4.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        log.info("Security Config Loaded!");  // 설정이 로드되는지 확인하는 로그
        http

                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/myinfo","/signup","/userreg","/loginform","/").permitAll()
                        .requestMatchers("/welcome","/shop/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );
        http
                .formLogin(form ->form
                        .loginPage("/loginform")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/welcome")
                )
                .logout(logout ->logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) //동시 접속 허용 개수
                        .maxSessionsPreventsLogin(false) // 디폴트 false 먼저 로그인한 사용자측에서 차단? 접근없어짐?  true - 두번째 로그인은 안됨
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}