package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //이 클래스가 설정 클래스(설정 파일)의 역할을 한다는 것을 알림
public class SecurityConfig {
    @Bean // 명시적 의존성 주입 : Autowired와 다름
    // 5.7버전 이전 WebSecurityConfigurerAdapter 사용
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                    .headers(headers -> headers
                    .addHeaderWriter((request, response) -> {
                    response.setHeader("X-XSS-Protection", "1; mode=block"); // XSS-Protection 헤더 설정
                    })
                    )
                    .csrf().disable() // csrf 공격 방지
                    .sessionManagement(session -> session
                                    .invalidSessionUrl("/session-expired") // 세션 만료시 이동 페이지
                                    .maximumSessions(1) // 사용자 별 세션 최대 수
                                    .maxSessionsPreventsLogin(true) // 동시 세션 제한
                    );
        return http.build(); // 필터 체인을 통해 보안설정(HttpSecurity)을 반환
    }
    private Customizer<CsrfConfigurer<HttpSecurity>> withDefaults() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withDefaults'");
    }
    @Bean // 암호화 설정
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // 비밀번호 암호화 저장
    }

}
