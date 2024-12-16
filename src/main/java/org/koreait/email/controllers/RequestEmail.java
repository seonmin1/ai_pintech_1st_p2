package org.koreait.email.controllers;

import lombok.Data;

import java.util.List;

/**
 * 이메일 커맨드 객체 생성
 */
@Data
public class RequestEmail {
    private List<String> to; // 받는쪽 이메일, 여러개 - 리스트 형태
    private List<String> cc; // 참조
    private List<String> bcc; // 숨은 참조
    private String subject; // 메일 제목
    private String content; // 메일 내용
}
