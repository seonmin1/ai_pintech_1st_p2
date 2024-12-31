package org.koreait.global.configs;

import org.koreait.member.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security 설정
 * - 접근 통제, 인가 기능 등 & 자동 로그인 기능
 */
@Configuration
@EnableMethodSecurity // 특정 메서드만 통제 가능한 기능
public class SecurityConfig {

    @Autowired
    private MemberInfoService memberInfoService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 인증 설정 S - 로그인, 로그아웃 */
        http.formLogin(c -> {
            c.loginPage("/member/login") // 로그인 양식을 처리할 주소
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .failureHandler(new LoginFailureHandler())
                    .successHandler(new LoginSuccessHandler()); // 성공 시 처리할 핸들러
        });

        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login");
        });
        /* 인증 설정 E - 로그인, 로그아웃 */

        /* 인가 설정 S - 페이지 접근 통제 */
        /**
         * 권한 통제 시 사용하는 메서드!
         * authenticated() : 인증받은 사용자만 접근 / 회원
         * anonymous() : 인증받지 않은 사용자만 접근 / 비회원
         * permitAll() : 모든 사용자가 접근 가능 / 회원 && 비회원
         * hasAuthority("권한 명칭") : 하나의 권한을 체크
         * hasAnyAuthority("권한1", "권한2", ...) : 나열된 권한 중 하나라도 충족하면 접근 가능
         * ROLE
         * ROLE_명칭
         * hasRole("명칭")
         * hasAnyRole(...)
         */
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/mypage/**", "/message/**").authenticated() // 인증 회원 - 마이페이지를 포함한 전체 하위경로 (**)
                    .requestMatchers("/member/login", "/member/join", "/member/agree").anonymous() // 미인증 회원
                    .requestMatchers("/admin/**").hasAnyAuthority("MANAGER", "ADMIN") // 관리자 페이지는 MANAGER, ADMIN 권한만 접근 가능
                    .anyRequest().permitAll(); // 나머지 페이지는 모두 접근
        });

        http.exceptionHandling(c -> {
           c.authenticationEntryPoint(new MemberAuthenticationExceptionHandler()) // 미로그인 시 인가 실패
                   .accessDeniedHandler(new MemberAccessDeniedHandler()); // 로그인 이후 인가 실패

        });

        /* 인가 설정 E - 페이지 접근 통제 */

        /* 자동 로그인 설정 S */
        http.rememberMe(c -> {
           c.rememberMeParameter("autoLogin") // 자동 로그인 설정
                   .tokenValiditySeconds(60 * 60 * 24 * 30) // 자동 로그인 유지 시간, 기본값 14일, 설정값 30일
                   .userDetailsService(memberInfoService)
                   .authenticationSuccessHandler(new LoginSuccessHandler());
        });
        /* 자동 로그인 설정 E */

        http.headers(c -> c.frameOptions(o -> o.sameOrigin())); // admin - iframe 사용 시 설정

        return http.build(); // 설정 무력화
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
