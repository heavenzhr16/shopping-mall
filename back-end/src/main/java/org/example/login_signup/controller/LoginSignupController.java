package org.example.login_signup.controller;

import lombok.RequiredArgsConstructor;
import org.example.login_signup.dto.*;
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
        System.out.println("🔑 반환되는 토큰: " + token.getToken());
        return ResponseEntity.ok(token);
    }

    // ✅ 이메일 인증 코드 발송
    @PostMapping("/email/send")
    public ResponseEntity<String> sendEmailCode(@RequestParam String email) {
        String code = loginSignupService.sendEmailVerificationCode(email);
        return ResponseEntity.ok("인증 코드 발송 완료 (임시 출력): " + code);
    }

    // ✅ 이메일 인증 코드 검증
    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyEmailCode(@RequestBody EmailVerifyRequestDto request) {
        boolean result = loginSignupService.verifyEmailCode(request.getEmail(), request.getCode());
        return result ? ResponseEntity.ok("이메일 인증 성공") : ResponseEntity.badRequest().body("이메일 인증 실패");
    }
}