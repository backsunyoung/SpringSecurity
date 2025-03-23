package com.example.securityexam.jwtexam.test.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/api/mypage")
    public String mypage(){
        return "home";
    }


    @GetMapping("/loginform")
    public String lofinform(){
        return "loginform";
    }


    @GetMapping("/mypageforweb")
    public String usingforweb(){
        return "infoforweb";
    }



}
