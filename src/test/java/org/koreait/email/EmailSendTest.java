package org.koreait.email;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.koreait.email.controllers.RequestEmail;
import org.koreait.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 이메일 인증 테스트
 */
@SpringBootTest
@ActiveProfiles({"default", "test", "email"})
public class EmailSendTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private EmailService service;

    @Test
    void test1() throws Exception {
        /**
         * to : 받는 이메일
         * cc : 참조
         * bc : 숨은 참조
         */
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        helper.setTo("iseonmin161@gmail.com"); // 내용을 보낼 메일 주소 기입
        helper.setSubject("테스트 이메일 제목..."); // 이메일 제목
        helper.setText("테스트 이메일 내용..."); // 이메일 내용
        javaMailSender.send(message);
    }

    @Test
    void test2() {
        Context context = new Context();
        context.setVariable("subject", "테스트 제목..."); // subject 를 value 값으로 번역

        String text = templateEngine.process("email/auth", context);
        System.out.println(text);
    }

    @Test
    void test3() {
        RequestEmail form = new RequestEmail();
        form.setTo(List.of("iseonmin161@gmail.com", "iseonmin161@gmail.com"));
        form.setCc(List.of("lsm1636@naver.com"));
        form.setBcc(List.of("lsm1636@naver.com"));
        form.setSubject("테스트 이메일 제목...");
        form.setContent("<h1>테스트 이메일 내용...</h1>");

        Map<String, Object> tplData = new HashMap<>();
        tplData.put("key1", "값1");
        tplData.put("key2", "값2");

        boolean result = service.sendEmail(form, "auth", tplData);
        System.out.println(result);
    }
}
