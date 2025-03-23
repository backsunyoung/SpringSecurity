package com.example.securityexam.jwtexam.test.Controller;



import com.example.securityexam.jwtexam.test.Domain.RefreshToken;
import com.example.securityexam.jwtexam.test.Domain.Role;
import com.example.securityexam.jwtexam.test.Service.RefreshTokenService;
import com.example.securityexam.jwtexam.test.Service.UserService;
import com.example.securityexam.jwtexam.test.dto.UserLoginResponseDto;
import com.example.securityexam.jwtexam.test.jwt.util.util.JwtTokenizer;
import com.example.securityexam.jwtexam.test.security.dto.UserLoginDto;
import com.example.securityexam.jwtexam.test.Domain.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;

    @GetMapping("/api/info")
    public String info(){
        return "info 인증완료&&&";
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response){
        // 1. username이 우리 서버에 있는지
        User user = userService.findByUsername(userLoginDto.getUsername());

        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 아이디");
        }

        // 2. 비번 비교
        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 올바르지 않습니다.");
        }
        //여기까지라면 유저 있고 비번 맞고

        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        //토큰발급
        String accessToken = jwtTokenizer.createAccessToken(user.getId(), user.getEmail(), user.getName(), user.getUsername(), roles);


        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(), user.getEmail(), user.getName(), user.getUsername(), roles);


        // 리프레시토큰을 데이터베이스에 저장
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setUserId(user.getId());

        refreshTokenService.addRefreshToken(refreshTokenEntity);


        //쿠키로도 저장
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.ACCESS_TOKEN_EXPIRE_COUNT/100)); // 초 단위




        Cookie refreshTokenCookie = new Cookie("RefreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.REFRESH_TOKEN_EXPIRE_COUNT/1000));

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);





        // 응답으로 보낼 값을 준비합니다.

        UserLoginResponseDto loginResponseDto = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .name(user.getName())
                .build();


        return ResponseEntity.ok(loginResponseDto);



    }


    // 1시이후

//    @PostMapping("/refreshToken")
//    public ResponseEntity<?> requestRefresh(){
//
//    }

}
