package com.example.securityexam.jwtexam.test.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegiserDTO {
    private String username;
    private String password;
    private String name;
    private String email;

    private List<String> roles;

}