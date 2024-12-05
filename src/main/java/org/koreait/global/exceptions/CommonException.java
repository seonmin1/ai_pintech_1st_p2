package org.koreait.global.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 모든 사용자 정의 예외 상위 클래스
 */
@Getter @Setter
public class CommonException extends RuntimeException {

    private HttpStatus status; // 응답 코드
    private boolean errorCode; // 에러 코드
    private Map<String, List<String>> errorMessages; // 에러 메세지

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = Objects.requireNonNullElse(status, HttpStatus.INTERNAL_SERVER_ERROR); // null 일 때 기본값 설정
    }

    /**
     * RestController에서 커맨드 객체 검증 실패 시 가공한 에러 메세지 정보
     * 같은 필드에 메세지가 여러개인 경우도 있으므로 반환값 자료형을 Object 설정
     */
    public CommonException(Map<String, List<String>> errorMessages, HttpStatus status) {
        this.errorMessages = errorMessages;
        this.status = status;
    }
}
