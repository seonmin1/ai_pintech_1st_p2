package org.koreait.email.exceptions;

import org.koreait.global.exceptions.BadRequestException;

/**
 * 인증코드가 일치하지 않을 경우 예외처리
 */
public class AuthCodeMismatchException extends BadRequestException {
    public AuthCodeMismatchException() {
        super("Mismatch.authCode");
        setErrorCode(true);
    }
}
