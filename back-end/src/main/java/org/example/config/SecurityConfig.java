package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // ✅ 추가
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    // ✅ 비밀번호 암호화를 위한 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 인증 매니저
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Spring Security 필터 체인 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/api/auth/**",
<<<<<<< HEAD
                        "/public/**",
                        "/login_signup/**", // 박준형 : 로그인, 회원가입 임시테스트
                        "/",
                        "/test"
                ).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
=======

                        "/review/**",

                        "/main/**",    // ✅ 메인 페이지 상품 리스트 요청
                        "/timeSale/**" // ✅ 한정 시간 상품 조회

                ).permitAll()
                .antMatchers("/admin/**", "/timeSale/changeProducts").hasRole("ADMIN")  // ✅ 관리자 전용 >> 이건 수정해야함
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // ✅ 일반 사용자 & 관리자 접근 >> 이건 수정해야함
>>>>>>> 4155211dd837ee599b5279362cd727339fa5ae23
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}