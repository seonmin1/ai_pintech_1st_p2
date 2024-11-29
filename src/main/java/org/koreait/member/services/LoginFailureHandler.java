package org.koreait.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.member.controllers.RequestJoin;
import org.koreait.member.controllers.RequestLogin;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        HttpSession session = request.getSession();
        RequestLogin form = Objects.requireNonNullElse((RequestLogin)session.getAttribute("requestLogin"), new RequestLogin());
        form.setErrorCodes(null);

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        /**
         * 1. 아이디 또는 비밀번호를 입력하지 않은 경우
         * 2. 아이디로 조회 안되는 경우
         * 3. 비번이 일치하지 않는 경우
         */
        if (exception instanceof BadCredentialsException) {
            List<String> errorCodes = Objects.requireNonNullElse(form.getErrorCodes(), new ArrayList<>());

            if (!StringUtils.hasText(email)) { // 이메일이 없을 때
                errorCodes.add("NotBlank_email");
            }

            if (!StringUtils.hasText(password)) { // 비밀번호가 없을 때
                errorCodes.add("NotBlank_password");
            }

            form.setErrorCodes(errorCodes);
        }

        session.setAttribute("requestLogin", form);

        // 로그인 실패 시 로그인 페이지로 이동
        response.sendRedirect(request.getContextPath() + "/member/login");
    }
}
