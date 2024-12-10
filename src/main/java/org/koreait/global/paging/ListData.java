package org.koreait.global.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 포켓몬 도감 목록 데이터 - 페이지가 있는 목록
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListData<T> {

    private T items; // 목록 데이터
    private Pagination pagination; // 페이징 기초 데이터

}
