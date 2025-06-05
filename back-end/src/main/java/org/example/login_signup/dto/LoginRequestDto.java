package org.example.login_signup.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private Long userId;          // 자체 로그인 전용
    private String password;
    private String email;         // 소셜 로그인 전용
    private String loginType;     // 소셜 로그인 전용
}
