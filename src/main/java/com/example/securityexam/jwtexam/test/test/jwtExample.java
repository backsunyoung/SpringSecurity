package com.example.securityexam.jwtexam.test.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class jwtExample {


    public static void main(String[] args) {


       // SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // 임의로 설정시 오류?
        String secret = "abcdefghijklmnopqrstuvwxzy123456";
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = Keys.hmacShaKeyFor(bytes);

        String jwt = Jwts.builder()
                .setIssuer("bsy-app")
                .setSubject("bsy123")
                .setExpiration(new Date(System.currentTimeMillis()*36000))
                .claim("role", "ADMIN")
                .signWith(secretKey)
                .compact();

        System.out.println("Jwt who created "+jwt+" !");


        // jwt 파싱, 검증

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();


        System.out.println(claims.getExpiration());
        System.out.println(claims.getSubject());

        System.out.println(claims.getAudience());
    }
}
