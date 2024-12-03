package org.koreait.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class MemberAuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        /**
         * 마이페이지 /mypage -> 로그인 페이지
         * 관리자 : 응답코드 401
         */
        String uri = request.getRequestURI(); // 요청 주소
        if (uri.contains("/mypage")) { // 마이페이지를 포함하고 있을 경우, 로그인 성공 시 마이페이지로 이동
            response.sendRedirect(request.getContextPath() + "/member/login?redirectUrl=/mypage");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 그외 401
        }
    }
}
