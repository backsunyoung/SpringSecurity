package com.example.securityexam.jwtexam.test.jwt.util.util;

import java.util.Arrays;

public class JwtTokenizerMain {

    public static void main(String[] args) {

        //byte[] secretKey = "12"


        JwtTokenizer jwtTokenizer = new JwtTokenizer("12345678901234567890123456789012", "12345678901234567890123456789012");

        String accessToken = jwtTokenizer.createAccessToken(2L, "test@test.com", "Testbsy", "testuser127", Arrays.asList("ROLE_USER"));


        System.out.println(accessToken);



        //Claim claim = jwtTokenizer.parseToken(accessToken, secret)

    }
}
