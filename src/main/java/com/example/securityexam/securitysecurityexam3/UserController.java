package com.example.securityexam.securitysecurityexam3;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class UserController {

    private final HomeService service;


    @GetMapping
    public String hello(){

        service.userLog();

        return " 로그인했다";



    }


    @GetMapping("/user")
    public String user(){
        return "일반회원입니다";
    }





}
