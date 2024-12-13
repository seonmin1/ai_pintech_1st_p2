package org.koreait.global.libs;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.services.FileInfoService;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 편의기능
 */
@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final MessageSource messageSource;
    private final FileInfoService fileInfoService;

    public boolean isMobile() {

        // 요청 헤더 - User-Agent 브라우저 정보 (모바일 or PC)
        String ua = request.getHeader("User-Agent");

        // 아래 문구가 포함되었는지, 아닌지로 패턴 체크
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        return StringUtils.hasText(ua) && ua.matches(pattern);
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
        Locale lo = request.getLocale(); // 사용자 요청 헤더에서 자동으로 가져온 Accept-Language (브라우저 언어 설정)

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

    /**
     * 이미지 출력
     * @param mode - image : 이미지 태그로 출력, background : 배경 이미지 형태로 출력
     * 다양한 형태로 사용하기 위해서 오버로드 함
     */
    public String showImage(Long seq, int width, int height, String mode, String className) {

        return showImage(seq, null, width, height, mode, className);
    }

    // mode 값이 없을 때 태그 값으로 고정
    public String showImage(Long seq, int width, int height, String className) {

        return showImage(seq, null, width, height, "image", className);
    }

    public String showBackground(Long seq, int width, int height, String className) {

        return showImage(seq, null, width, height, "background", className);
    }

    public String showImage(String url, int width, int height, String mode, String className) {

        return showImage(null, url, width, height, mode, className);
    }

    public String showImage(String url, int width, int height, String className) {

        return showImage(null, url, width, height, "image", className);
    }

    public String showBackground(String url, int width, int height, String className) {

        return showImage(null, url, width, height, "background", className);
    }

    public String showImage(Long seq, String url, int width, int height, String mode, String className) {
        try {
            String imageUrl = null;

            if (seq != null && seq > 0L) {
                FileInfo item = fileInfoService.get(seq);

                if (!item.isImage()) {
                    return "";
                }

                imageUrl = String.format("%s&with=%d&height=%d", item.getThumbUrl(), width, height);

            } else if (StringUtils.hasText(url)) {
                imageUrl = String.format("%s/api/file/thumb?url=%s&width=%d&height=%d",
                                            request.getContextPath(), url, width, height);
            }

            if (!StringUtils.hasText(imageUrl)) return ""; // imageUrl 없을 때 출력 X

            mode = Objects.requireNonNullElse(mode, "image");
            className = Objects.requireNonNullElse(className, "image");

            if (mode.equals("background")) { // 배경 이미지

                return String.format("<div style='width: %dpx; height: %dpx; background: url(\"%s\") no-repeat center center; background-size: cover;' class='%s'></div>", width, height, imageUrl, className);

            } else { // 이미지 태그
                return String.format("<img src='%s' class='%s'>", imageUrl, className);
            }

        } catch (Exception e) {}

        return ""; // 오류 방지로 빈문자열 넣어줌
    }
}
