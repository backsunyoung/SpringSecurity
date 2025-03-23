package com.example.securityexam.jwtexam.test.Domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "refresh_token")
@Getter
@Setter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String value;
}