package org.koreait.global.paging;

import lombok.Data;

/**
 * 포켓몬 도감 공통 검색 항목
 */
@Data
public class CommonSearch {

    private int page = 1; // 페이지 번호, 기본값 1
    private int limit = 20; // 페이지당 출력 갯수, 기본값 20

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드

}
