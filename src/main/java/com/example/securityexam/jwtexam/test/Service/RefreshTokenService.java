package com.example.securityexam.jwtexam.test.Service;


import com.example.securityexam.jwtexam.test.Domain.RefreshToken;
import com.example.securityexam.jwtexam.test.Repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public RefreshToken addRefreshToken(RefreshToken refreshToken){
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken){
        refreshTokenRepository.findByValue(refreshToken).ifPresent(refreshTokenRepository::delete);

    }


    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByValue(refreshToken);
    }



}
