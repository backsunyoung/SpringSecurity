package com.example.securityexam.securityexam;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/hello", "/loginForm", "/login", "/").permitAll() //인증 없이 접근 가능
                        .anyRequest().authenticated() //모든 요청에 대해 인증을 요규하겠다

                ).formLogin(formLogin -> formLogin
                        //.loginPage("/login") // 원하는 로그인 페이지 설정
                        .defaultSuccessUrl("/success") // 인증에 성공하면 가고싶은 페이지 설정
                        .failureUrl("/fail")
                        .usernameParameter("userId")
                        .passwordParameter("password"));
        //.formLogin(Customizer.withDefaults());


        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/hello")
                        //
                        .addLogoutHandler((request, response, authentication) -> {
                            log.info("로그아웃 세션, 쿠키 삭제 ");

                            HttpSession session = request.getSession();
                            if(session != null){
                                session.invalidate(); //세션 삭제
                            }
                        }
                        )
                        .deleteCookies("JSESSIONID")//로그아웃시에 원하는쿠키 삭제게할수잇음

                );


        return http.build();


        //로그아웃


    }
}
