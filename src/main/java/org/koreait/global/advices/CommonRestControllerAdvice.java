package org.koreait.global.advices;

import org.koreait.global.annotations.RestExceptionHandling;
import org.koreait.global.exceptions.CommonException;
import org.koreait.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * RestController 공통부분 처리
 */
@RestControllerAdvice(annotations = RestExceptionHandling.class) // annotations - 범위 설정
public class CommonRestControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData> errorHandler(Exception e) { // 에러 확인이 주 이기 때문에 매개변수로 Exception 설정

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 기본 에러 코드 500으로 고정

        Object message = e.getMessage(); // 기본 메세지

        if (e instanceof CommonException commonException) {
            status = commonException.getStatus();

            Map<String, Object> errorMessages = commonException.getErrorMessages();

            if (errorMessages != null) { // 에러 메세지 값이 있을 때 메세지에 에러 메세지 대체
                message = errorMessages;
            }
        }

        JSONData data = new JSONData();
        data.setSuccess(false); // 에러 시 false
        data.setStatus(status); // 응답 상태
        data.setMessage(message); // 응답 메세지

        e.printStackTrace(); // 에러 위치 확인

        return ResponseEntity.status(status).body(data);
    }
}
