package org.koreait.member.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RequestLogin {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
    private String redirectUrl; // 로그인 완료 후 이동할 주소 - 없으면 메인페이지

    private List<String> errorCodes; // 에러가 있으면 이메일, 비밀번호 검증
}
