package com.example.securityexam.securityexam2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }




    @GetMapping("/info")
    public String info(){
        return "info";


    }


    @GetMapping("/hello")
    public String hello(){
        return "hello";

    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm page";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }
    @GetMapping("/fail")
    public String fail(){
        return "fail";
    }
}
