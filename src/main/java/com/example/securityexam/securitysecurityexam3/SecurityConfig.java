package com.example.securityexam.securitysecurityexam3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws  Exception{
        http
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/hello", "/shop/**", "/userinfo", "/img/**", "/static/**", "/js/**", "/css/**").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "SUPERUSER")
                        .requestMatchers("/admin/abc").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERUSER")
                        .anyRequest().authenticated()

                )

                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/hello")

                )

                .rememberMe(remember -> remember
                        .key("my_secure_key")
                        .tokenValiditySeconds(5)
                        .rememberMeParameter("remembermebsy")
                );


                return http.build();

    }


    // 유저, 어드민, 슈퍼유저 섹개의 롤 이미 존재한다 가정

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("123"))
                .roles("USER")
                .build();


        UserDetails user2 = User.withUsername("bsy")
                .password(passwordEncoder.encode("3212"))
                .roles("USER", "ADMIN") // 어드민은 유저를 포함하고 어쩌고저쩌고 상속
                .build();



        UserDetails user3 = User.withUsername("gold")
                .password(passwordEncoder.encode("567")) // 꼭반드시 암호화하여
                .roles("ADMIN") // 어드민은 유저를 포함하고 어쩌고저쩌고 상속
                .build();


        UserDetails user4 = User.withUsername("superuser")
                .password(passwordEncoder.encode("5678")) // 꼭반드시 암호화하여
                .roles("SUPERUSER") // 어드민은 유저를 포함하고 어쩌고저쩌고 상속
                .build();


        return new InMemoryUserDetailsManager(user, user2, user3, user4);


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
