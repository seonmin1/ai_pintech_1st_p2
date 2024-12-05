package org.koreait.global.libs;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final MessageSource messageSource;

    public boolean isMobile() {

        // 요청 헤더 - User-Agent 브라우저 정보
        String ua = request.getHeader("User-Agent");
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        return ua.matches(pattern);
    }

    /**
     * mobile, front 템플릿 분리 함수
     * - mobile 이면 mobile, pc 이면 front
     */
    public String tpl(String path) {
        String prefix = isMobile() ? "mobile" : "front";

        return String.format("%s/%s", prefix, path);
    }

    /**
     * 메세지 코드로 조회된 문구
     */
    public String getMessage(String code) {
        Locale lo = request.getLocale(); // 사용자 요청 헤더 (Accept-Language)

        return messageSource.getMessage(code, null, lo);
    }

    // 코드를 배열로 받았을 때 리스트로 바꿔주는 메서드
    public List<String> getMessages(String[] codes) {

            return Arrays.stream(codes).map(c -> {

                try {
                    return getMessage(c);
                } catch (Exception e) {
                    return "";
                }
            }).filter(s -> !s.isBlank()).toList(); // 빈 문자열이 아닐 경우 값을 가져와서 리스트화
    }

    /**
     * REST 커맨드 객체 검증 실패시에 에러 코드를 가지고 메세지 추출
     */
    public Map<String, List<String>> getErrorMessages(Errors errors) {

        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false);

        try {
            // 필드별 에러코드 - getFieldErrors()
            Map<String, List<String>> messages = errors.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, f -> getMessages(f.getCodes()), (v1, v2) -> v2)); // v1 처음값, v2 중복값

            // 글로벌 에러코드 - getGlobalErrors()
            List<String> gMessages = errors.getGlobalErrors()
                    .stream()
                    .flatMap(o -> getMessages(o.getCodes()).stream()) // [[..]] 형태를 [..] 형태로 변환하기위해 flatMap() 사용
                    .toList();

            // 글로벌 에러코드 필드 - global 고정
            if (!gMessages.isEmpty()) {
                messages.put("global", gMessages);
            }

            return messages; // 임시

        } finally {
            // 싱글톤 객체이므로 변경하면 영향을 줌 -> 다시 true로 변경해줘야 함
            ms.setUseCodeAsDefaultMessage(true);
        }
    }
}
