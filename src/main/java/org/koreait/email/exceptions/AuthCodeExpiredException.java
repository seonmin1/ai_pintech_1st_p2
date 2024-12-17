package org.koreait.email.exceptions;

import org.koreait.global.exceptions.BadRequestException;

/**
 * 인증코드 만료 예외처리
 */
public class AuthCodeExpiredException extends BadRequestException {

    public AuthCodeExpiredException() {
        super("Expired.authCode");
        setErrorCode(true);
    }
}
