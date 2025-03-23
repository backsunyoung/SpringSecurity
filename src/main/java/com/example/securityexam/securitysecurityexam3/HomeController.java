package com.example.securityexam.securitysecurityexam3;

import org.apache.logging.log4j.message.Message;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(

    ){

        String message = null;


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println(message);
            message="@@@ 로그인된 사용자가 없습니다.";
            //;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;

            message = "@@ 현재 로그인한 사용자 "+ userDetails.getUsername();

            //Message message = new

            System.out.println(message);
            System.out.println(message);
        } else {

            message = "@ 현재 로그인한 사용자: " + principal.toString();
        }

        return message;
    }


    @GetMapping("/user")
    public String user(){
        return "일반회원입니다";
    }

    @GetMapping("/ccc")
    public String ccc(@AuthenticationPrincipal UserDetails userDetails){
        return "ccc"+userDetails.getUsername();
    }

}
