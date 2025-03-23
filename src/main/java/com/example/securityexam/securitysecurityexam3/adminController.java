package com.example.securityexam.securitysecurityexam3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class adminController {

    @GetMapping
    public String admin(){
        return "관리자입니다";
    }


    @GetMapping("/abc")
    public String adminAbc(){
        return "찐 최종관리자입니다";
    }


}
