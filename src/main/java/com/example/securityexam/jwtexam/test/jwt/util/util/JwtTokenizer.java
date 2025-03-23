package com.example.securityexam.jwtexam.test.jwt.util.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenizer {

    private final byte[] accessSecret;
    private final byte[] refreshSecret;
    // primitive타입 객체 타입
    public static final Long ACCESS_TOKEN_EXPIRE_COUNT = 30*60*1000L; //유지시간 30분
    public static final Long REFRESH_TOKEN_EXPIRE_COUNT = 7*24*60*60*1000L; // 유지시간 7일

    public JwtTokenizer(@Value("${jwt.secretKey}")String accessSecret, @Value("${jwt.refreshKey}") String refreshSecret) {
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
    }


    private String createToken(Long id, String email, String name, String username, List<String> roles,
                               Long expire, byte[] secretKey){

        Claims claims = Jwts.claims().setSubject(String.valueOf(id)); // 서브젝트의 가장중요한, 변하지 않는 값? 이메일이라던지? 클레임에 들어가는것들중 대표정보
        claims.put("name", name);
        claims.put("userId", id);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expire)) // expire 1000 * 60 * 60 1시간
                .signWith(getSigningkey(secretKey))
                .compact();
    }

    private static Key getSigningkey(byte[] secretKey){
        return Keys.hmacShaKeyFor(secretKey);
    }

    // 엑세스 토큰 생성
    public String createAccessToken(Long id, String email, String name, String username, List<String> roles){

        return createToken(id, email, name, username, roles, ACCESS_TOKEN_EXPIRE_COUNT, accessSecret);
    }

    public String createRefreshToken(Long id, String email, String name, String username, List<String> roles){
        return createToken(id, email, name, username, roles, REFRESH_TOKEN_EXPIRE_COUNT, accessSecret);
    }



    public Claims parseToken(String token, byte[] secretKey){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningkey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims parseAccessToken(String accessToken){
        return parseToken(accessToken, accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken){
        return parseToken(refreshToken, refreshSecret);
    }

    public Long getUserIdFromToken(String token){
        if(token == null || token.isBlank()){
            throw new IllegalArgumentException("Jwt 토큰이 없습니다.");

        }


        if(!token.startsWith("Bearer")){
            throw new IllegalArgumentException("유효하지 않은 형식입니다.");
        }


        Claims claims = parseToken(token, accessSecret);
        if(claims == null){
            throw new IllegalArgumentException("유효하지 않은 형식");
        }

        Object userId = claims.get("userId");
        if(userId instanceof Number){
            return ((Number)userId).longValue();

        }else {
            throw new IllegalArgumentException("JWT토큰에서 userId를 찾을 수 없습니다.");
        }
    }
}
