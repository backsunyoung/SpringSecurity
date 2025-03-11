package com.example.securityexam.oauthexam.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialUserRequestDto {
    @NotBlank(message = "Provider은 필수")
    private String provider;
    private String socialId;
    private String name;
    private String email;
    private String username;

    private String uuid; // 유효아이디?
}
