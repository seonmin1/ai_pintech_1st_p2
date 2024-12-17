package org.koreait.email.exceptions;

import org.koreait.global.exceptions.BadRequestException;

/**
 * 인증 코드 발급 실패 시 예외처리
 */
public class AuthCodeIssueException extends BadRequestException {
    public AuthCodeIssueException() {
        super("Fail.authCode.issue");
        setErrorCode(true);
    }
}
