package org.koreait.mypage.controllers;

import lombok.Data;

/**
 * 마이페이지에서 변경가능한 요소 정의
 */
@Data
public class RequestProfile {

    private String name; // 회원명
    private String nickName; // 닉네임
    private String password; // 비밀번호
    private String confirmPassword; // 비밀번호 확인
}
