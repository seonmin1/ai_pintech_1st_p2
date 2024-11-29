package org.koreait.global.configs;

import org.koreait.member.services.LoginFailureHandler;
import org.koreait.member.services.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security 설정 - 접근 통제, 인가 기능 등
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 인증 설정 S - 로그인, 로그아웃 */
        http.formLogin(c -> {
           c.loginPage("/member/login") // 로그인 양식을 처리할 주소
                   .usernameParameter("email")
                   .passwordParameter("password")
                   .failureHandler(new LoginFailureHandler()) // 로그인 실패 시 이동할 주소
                   .successHandler(new LoginSuccessHandler()); // 로그인 성공 시 이동할 주소

        });

        http.logout(c -> {
          c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                  .logoutSuccessUrl("/member/login"); // 로그아웃 시 이동할 주소
        });
        /* 인증 설정 E - 로그인, 로그아웃) */

        return http.build(); // 설정 무력화
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호를 생성하고 검증
        return new BCryptPasswordEncoder(); // 유동 해시 사용
    }
}
