package com.example.securityexam.beforesecurity;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() throws InterruptedException{

        log.info("UserConttoller hello() 실행"+Thread.currentThread().getName());

        Thread.sleep(1000);

        userService.test();


        return "hellooo "+UserContext.getUser().getName();
    }

    @GetMapping("/test/hi")
    public String hi ()throws InterruptedException{

        Thread.sleep(5000);

        return "hi";


    }


}
