package org.example.login_signup.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.UsersEntity;
import org.example.jwt.JwtUtil;
import org.example.login_signup.dto.*;
import org.example.login_signup.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginSignupService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ✅ 이메일 인증 코드 저장용 (임시 Map, 실제 서비스는 Redis 권장)
    private final Map<String, String> emailCodeMap = new HashMap<>();

    // 회원가입
    public void signup(SignupRequestDto request) {
        if (usersRepository.findByEmailAndLoginType(request.getEmail(), request.getLoginType()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 계정입니다.");
        }

        if (usersRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        String encodedPassword = (request.getPassword() != null && !request.getPassword().isBlank())
                ? passwordEncoder.encode(request.getPassword())
                : UUID.randomUUID().toString();

        UsersEntity user = new UsersEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setBirthdate(request.getBirthdate());
        user.setNickname(request.getNickname());
        user.setPhoneNum(request.getPhoneNum());
        user.setLoginType(request.getLoginType());
        user.setUserStatus("ACTIVE");
        user.setJoinedAt(LocalDateTime.now());

        usersRepository.save(user);
    }

    // 로그인
    public TokenResponseDto login(LoginRequestDto request) {
        UsersEntity user;

        if (request.getUserId() != null) {
            user = usersRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

        } else if (request.getEmail() != null && request.getLoginType() != null) {
            user = usersRepository.findByEmailAndLoginType(request.getEmail(), request.getLoginType())
                    .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 소셜 계정입니다."));
        } else {
            throw new IllegalArgumentException("로그인 정보가 부족합니다.");
        }

        user.setLastLoginAt(LocalDateTime.now());
        usersRepository.save(user);

        String token = jwtUtil.createToken(
                user.getUsername(),
                user.getEmail(),
                "USER"
        );

        return new TokenResponseDto(token);
    }

    // 이메일 인증 코드 발송
    public String sendEmailVerificationCode(String email) {
        String code = UUID.randomUUID().toString().substring(0, 6);
        emailCodeMap.put(email, code);

        // 실제로는 이메일 전송 서비스 호출
        System.out.println("📧 이메일 인증 코드: " + code);

        return code;
    }

    // 이메일 인증 코드 검증
    public boolean verifyEmailCode(String email, String code) {
        String savedCode = emailCodeMap.get(email);
        return savedCode != null && savedCode.equals(code);
    }
}