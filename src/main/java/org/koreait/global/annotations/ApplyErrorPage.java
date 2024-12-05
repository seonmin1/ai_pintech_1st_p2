package org.koreait.global.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 마커 애너테이션 설정
 * - RestController가 Controller를 상속받는 구조이므로 해당 애너테이션으로 에러페이지를 구분하기 위해 생성
 * - MemberController 클래스에서 사용
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplyErrorPage {
}
