package org.koreait.admin.global.menu;

/**
 * 관리자 페이지 메뉴 구성
 * @param code : 서브 메뉴 코드
 * @param name : 서브 메뉴 이름
 * @param url : 서브 메뉴 이동 URL
 */
public record MenuDetail(
   String code,
   String name,
   String url
) {}
