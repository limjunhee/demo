package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean // 명시적 의존성 주입 : Autowired와 다름
    // 5.7버전 이저 WebSecurityConfigurerAdapter 사용
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // 설정을 비워둠
    return http.build(); // 필터 체인을 통해 보안설정(HttpSecurity)을 반환
    }
    @Bean // 암호화 설정
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // 비밀번호 암호화 저장
    }

}
