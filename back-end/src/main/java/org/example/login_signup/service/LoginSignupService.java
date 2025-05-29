package org.example.login_signup.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.UsersEntity;
import org.example.login_signup.dto.*;
import org.example.login_signup.repository.UsersRepository;
import org.example.jwt.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginSignupService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequestDto request) {
        // 이메일 + loginType 중복 방지
        if (usersRepository.findByEmailAndLoginType(request.getEmail(), request.getLoginType()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 계정입니다.");
        }

        String encodedPassword = (request.getPassword() != null && !request.getPassword().isBlank())
                ? passwordEncoder.encode(request.getPassword())
                : UUID.randomUUID().toString(); // 소셜용 더미 비번

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

    public TokenResponseDto login(LoginRequestDto request) {
        UsersEntity user;

        if (request.getUserId() != null) {
            // 자체 로그인
            user = usersRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

        } else if (request.getEmail() != null && request.getLoginType() != null) {
            // 소셜 로그인
            user = usersRepository.findByEmailAndLoginType(request.getEmail(), request.getLoginType())
                    .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 소셜 계정입니다."));
        } else {
            throw new IllegalArgumentException("로그인 정보가 부족합니다.");
        }

        user.setLastLoginAt(LocalDateTime.now());
        usersRepository.save(user);

        String token = jwtUtil.createToken(
                user.getUsername(),        // subject
                user.getEmail(),           // claim "email"
                "USER"                     // claim "role"
        );
        return new TokenResponseDto(token);
    }
}