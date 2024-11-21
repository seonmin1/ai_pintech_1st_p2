package org.koreait.global.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * 모든 사용자 정의 예외 상위 클래스
 */
@Getter @Setter
public class CommonException extends RuntimeException {

    private HttpStatus status; // 응답 코드
    private boolean errorCode; // 에러 코드

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = Objects.requireNonNullElse(status, HttpStatus.INTERNAL_SERVER_ERROR); // null 일 때 기본값 설정
    }
}
