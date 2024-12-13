package org.koreait.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.member.controllers.RequestLogin;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 로그인 실패 시 수행할 핸들러
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        /**
         * 검증을 위한 준비작업
         */
        // 세션쪽에 값 설정
        HttpSession session = request.getSession();
        RequestLogin form = Objects.requireNonNullElse((RequestLogin)session.getAttribute("requestLogin"), new RequestLogin());
        form.setErrorCodes(null);

        // 검증할 값 가져오기 - email, password
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        form.setEmail(email);
        form.setPassword(password);

        String redirectUrl = request.getContextPath() + "/member/login";

        /**
         * 1. 아이디 또는 비밀번호를 입력하지 않은 경우
         * 2. 아이디로 조회 안되는 경우
         * 3. 비번이 일치하지 않는 경우
         */
        if (exception instanceof BadCredentialsException) {
            List<String> errorCodes = Objects.requireNonNullElse(form.getErrorCodes(), new ArrayList<>());

            // 이메일을 입력하지 않았을 때
            if (!StringUtils.hasText(email)) {
                errorCodes.add("NotBlank_email");
            }

            // 비밀번호를 입력하지 않았을 때
            if (!StringUtils.hasText(password)) {
                errorCodes.add("NotBlank_password");
            }

            // 아이디, 비번이 일치하지 않을 때
            if (errorCodes.isEmpty()) {
                errorCodes.add("Failure.validate.login");
            }

            form.setErrorCodes(errorCodes);
        } else if (exception instanceof CredentialsExpiredException) { // 비밀번호가 만료된 경우 변경페이지로 이동

            redirectUrl = request.getContextPath() + "/member/password/change";
        } else if (exception instanceof DisabledException) { // 탈퇴한 회원일 경우
            form.setErrorCodes(List.of("Failure.disabled.login"));
        }

        session.setAttribute("requestLogin", form);

        // 로그인 실패 시 로그인 페이지로 이동
        response.sendRedirect(redirectUrl);
    }
}
