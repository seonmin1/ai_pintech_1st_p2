package org.koreait.file.controllers;

import lombok.Data;

/**
 * 썸네일 관련 커맨드 객체 추가
 * 의존성 추가
 */
@Data
public class RequestThumb {
    private Long seq;
    private String url; // 원격 이미지 URL
    private int width;
    private int height;
}
