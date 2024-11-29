package org.koreait.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정 - 접근 통제, 인가 기능 등
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.build(); // 설정 무력화
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호를 생성하고 검증
        return new BCryptPasswordEncoder(); // 유동 해시 사용
    }
}
