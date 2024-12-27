package org.koreait.admin.member.controllers;

import lombok.Data;
import org.koreait.global.paging.CommonSearch;
import org.koreait.member.constants.Authority;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 회원 조회 커맨드 객체
 * - 여러명 조회 가능하게끔 List 정의
 */
@Data
public class MemberSearch extends CommonSearch {
    private List<String> email; // 이메일
    private List<Authority> authority; // 권한
    private String dateType; // 검색기준 - 타입이 없으면 가입일자를 기준으로 검색

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sDate; // 검색날짜 시작일

    @DateTimeFormat(pattern = "yyyy-MM-dd") // 날짜 형식 지정
    private LocalDate eDate; // 검색날짜 종료일
}
