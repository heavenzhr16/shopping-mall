package org.example.login_signup.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;
    private String email;
    private String password;
    private String birthdate;
    private String nickname;
    private Long phoneNum;
    private String loginType;
}
