package org.example.login_signup.controller;

import lombok.RequiredArgsConstructor;
import org.example.login_signup.dto.LoginRequestDto;
import org.example.login_signup.dto.SignupRequestDto;
import org.example.login_signup.dto.TokenResponseDto;
import org.example.login_signup.service.LoginSignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginSignupController {

    private final LoginSignupService loginSignupService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        loginSignupService.signup(requestDto);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        TokenResponseDto token = loginSignupService.login(requestDto);
        return ResponseEntity.ok(token);
    }
}
