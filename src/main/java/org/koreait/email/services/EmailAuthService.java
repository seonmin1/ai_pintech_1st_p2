package org.koreait.email.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.email.controllers.RequestEmail;
import org.koreait.email.exceptions.AuthCodeExpiredException;
import org.koreait.email.exceptions.AuthCodeMismatchException;
import org.koreait.global.exceptions.BadRequestException;
import org.koreait.global.libs.Utils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 이메일 인증 서비스
 * - 인증코드와 만료시간 설정
 */
@Service
@Profile("email")
@RequiredArgsConstructor
public class EmailAuthService {

    private final Utils utils;
    private final EmailService emailService;
    private final HttpSession session;

    /**
     * @param to : 수신쪽 이메일 주소
     */
    public boolean sendCode(String to) {
        Random random = new Random();
        String subject = utils.getMessage("Email.authCode.subject");

        /**
         * 인증코드는 5자리 정수
         * 만료시간을 3분으로 기록
         * 사용자의 입력을 검증하기 위해서 세션에 인증코드와 만료시간을 기록
         */
        Integer authCode = random.nextInt(99999); // 5자리 정수를 랜덤으로 받음

        LocalDateTime expired = LocalDateTime.now().plusMinutes(3L); // 만료시간을 현재시간에서 3분뒤로

        session.setAttribute("authCode", authCode); // 인증코드
        session.setAttribute("expiredTime", expired); // 만료시간
        session.setAttribute("authCodeVerified", false);

        Map<String, Object> tplData = new HashMap<>();
        tplData.put("authCode", authCode);

        RequestEmail form = new RequestEmail();
        form.setTo(List.of(to));
        form.setSubject(subject);

        return emailService.sendEmail(form, "auth", tplData);
    }

    /**
     * 인증코드 검증
     * - 예외 발생하면 인증 실패, 발생하지 않으면 인증 성공
     * @param code : 사용자가 입력한 인증 코드 (비교대상)
     */
    public void verify(Integer code) {
        if (code == null) { // 사용자가 코드를 입력하지 않은 경우
            throw new BadRequestException(utils.getMessage("NotBlank.authCode"));
        }

        LocalDateTime expired = (LocalDateTime) session.getAttribute("expiredTime");
        Integer authCode = (Integer) session.getAttribute("authCode");

        if (expired != null && expired.isBefore(LocalDateTime.now())) { // 코드가 만료된 경우
            throw new AuthCodeExpiredException();
        }

        if (authCode == null) {
            throw new BadRequestException();
        }

        if (!code.equals(authCode)) { // 인증 코드가 일치하지 않는 경우
            throw new AuthCodeMismatchException();
        }

        // 인증 성공 상태 세션에 기록
        session.setAttribute("authCodeVerified", true);
    }
}
