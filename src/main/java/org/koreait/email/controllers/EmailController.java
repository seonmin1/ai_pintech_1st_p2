package org.koreait.email.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.email.exceptions.AuthCodeIssueException;
import org.koreait.email.services.EmailAuthService;
import org.koreait.global.rests.JSONData;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Profile("email")
@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final EmailAuthService authService;

    /**
     * 인증 코드 발급
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/auth/{to}")
    public void authCode(@PathVariable("to") String to) {
        if (!authService.sendCode(to)) { // 인증 코드 발급 실패 시
            throw new AuthCodeIssueException();
        }
    }

    /**
     * 발급 받은 인증 코드 검증
     */
    @GetMapping("/verify")
    public void verify(@RequestParam(name = "authCode", required = false) Integer authCode) {
        authService.verify(authCode);
    }
}
