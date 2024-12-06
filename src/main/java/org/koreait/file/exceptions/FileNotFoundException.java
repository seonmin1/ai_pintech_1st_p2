package org.koreait.file.exceptions;

import org.koreait.global.exceptions.scripts.AlertBackException;
import org.springframework.http.HttpStatus;

/**
 * 파일이 없으면 뒤로 돌아가야하므로 CommonException 상속 대신 AlertBackException 상속
 */
public class FileNotFoundException extends AlertBackException {
    public FileNotFoundException() {
        super("NotFound.file", HttpStatus.NOT_FOUND);
        setErrorCode(true); // "NotFound.file" 텍스트가 들어가면 에러코드로 응답
    }
}
