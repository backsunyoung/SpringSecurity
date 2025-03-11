package com.example.securityexam.oauthexam.controller;

import com.example.securityexam.oauthexam.dto.SocialUserRequestDto;
import com.example.securityexam.oauthexam.entity.SocialLoginInfo;
import com.example.securityexam.oauthexam.service.SocialLoginInfoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import com.example.securityexam.oauthexam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.securityexam.oauthexam.security.CustomUserDetails;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    private final SocialLoginInfoService socialLoginInfoService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "oauth/welcome";
    }



    @GetMapping("/loginform")
    public String loginform(){
        return "oauth/user/loginform";
    }

    @GetMapping("/registerSocialUser")
    public String registerSocialUser(@RequestParam("provider") String provider, @RequestParam("socialId") String socialId,
                                     @RequestParam("name") String name, @RequestParam("uuid") String uuid, Model model){
        model.addAttribute("provider", provider);
        model.addAttribute("socialId", socialId);
        model.addAttribute("name", name);
        model.addAttribute("uuid", uuid);


        return "oauth/user/registerSocialUser";
    }

    @PostMapping("/saveSocialUser")
    public String saveSocial(@ModelAttribute SocialUserRequestDto requestDto){
        Optional<SocialLoginInfo> socialLoginInfoOptional = socialLoginInfoService.findByProviderAndUuidAndSocialId(requestDto.getProvider(), requestDto.getUuid(), requestDto.getSocialId());

        if(socialLoginInfoOptional.isPresent()){
            SocialLoginInfo socialLoginInfo = socialLoginInfoOptional.get();
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(socialLoginInfo.getCreateAt(), now);
            if(duration.toMinutes() > 100){
                return "redirect:/error";
            }

            userService.saveUser(requestDto, passwordEncoder);
            return "redirect:/";

        }else {
            return "redirect:/error";
        }



    }


    @GetMapping("/info")
    public String info(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        model.addAttribute("user", customUserDetails);

        return "oauth/info";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.logout(); // 강제 로그아웃 처리
        } catch (ServletException e) {
            e.printStackTrace();
        }


        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";

    }


}
