package org.koreait.global.validators;

public interface PasswordValidator {
    /**
     * 알파벳 복잡성 체크
     * 1) 대소문자 각각 1개 이상 있는 경우
     * 2) 대소문자 구분없이 알파벳 1자 이상
     * - caseInsensitive : true -> 2번, false -> 1번
     */
    default boolean alphaCheck(String password, boolean caseInsensitive) {
        // 대소문자 구분없이 알파벳 1자 이상
        if (caseInsensitive) {
             // .* : 0개 이상 아무 문자
             // [a-zA-Z]+ : 알파벳 대소문자 상관없이 1자 이상
            return password.matches(".*[a-zA-Z]+.*");
        }
        // 대문자 1개 이상 && 소문자 1개 이상
        return password.matches(".*[a-z]+.*") && password.matches(".*[A-Z]+.*");
    }

    /**
     * 숫자 복잡성 체크
     * [0~9] : \d (역슬래시 \\ 두개 붙여야 한개로 인식)
     */
    default boolean numberCheck(String password) {
        // 0~9까지 숫자
        return password.matches(".*\\d.*");
    }

    /**
     * 특수문자 복잡성 체크
     * [^문자..] : 문자는 제외
     * [^\d] : 숫자를 제외한 문자
     * [^!@#$%...] : 특수문자 제외 - 특수문자는 많으므로 특수문자를 제외하기보단 문자, 숫자를 제외하는 것이 좋음
     * ^ : 제외를 의미
     */
    default boolean specialCharsCheck(String password) {
        // 숫자, 알파벳, 한글을 제외한 모든 문자 : 특수문자
        String pattern = ".*[^0-9a-zA-Zㄱ-ㅎ가-힣].*";

        return password.matches(pattern);
    }
}
