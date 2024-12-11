package org.koreait.global.paging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 포켓몬 도감 페이지 설정
 * 고려할 사항
 * - 페이지 구간: (현재 페이지 번호 - 1) /  5
 * - 시작페이지: 구간번호 * 5 + 1
 * - 종료페이지: 구간번호 + 5 - 1
 * - 마지막 페이지 구간의 갯수 (1개여도 1페이지를 차지하므로 올림 처리)
 */
@Getter
@ToString
public class Pagination {

    private int page;
    private int total;
    private int ranges;
    private int limit;

    private int totalPages;
    private int firstRangePage;
    private int lastRangePage;
    private int prevRangeLastPage;
    private int nextRangeFirstPage;

    private String baseUrl; // 기본 주소

    /**
     * 기초데이터
     * @param page : 현재 페이지 번호
     * @param total : 총 레코드 갯수
     * @param ranges : 페이지 구간 갯수
     * @param limit : 한 페이지당 출력될 레코드 갯수
     */

    public Pagination(int page, int total, int ranges, int limit) {
        this(page, total, ranges, limit, null);
    }

    public Pagination(int page, int total, int ranges, int limit, HttpServletRequest request) {
        // 페이징 기본값 처리 - 페이지가 1보다 작은 경우 1로 대체
        page = Math.max(page, 1);

        // 총 레코드 갯수 기본값 처리 - 0보다 작은 경우 0으로 대체
        total = Math.max(total, 0);

        // 페이지 구간 갯수를 최소 10으로 고정
        ranges = ranges < 1 ? 10 : ranges;

        // 한 페이지당 출력 갯수를 최소 20개로 고정
        limit = limit < 1 ? 20 : limit;

        // 총 레코드 갯수가 0일 때 처리 안함
        if (total == 0) {
            return;
        }

        // 전체 페이지 갯수 - 올림처리, 형변환
        int totalPages = (int) Math.ceil(total / (double) limit);

        // 페이지가 소속된 구간 번호 - 0, 1, 2 ...
        // 정수 형태로 버림되므로 형변환 필요 X
        int rangeCnt = (page - 1) / ranges;

        // 현재 구간의 시작 페이지 번호 - 구간번호 * 5 + 1
        int firstRangePage = rangeCnt * ranges + 1;

        // 현재 구간의 종료 페이지 번호 - 구간번호 + 5 - 1
        int lastRangePage = firstRangePage + ranges - 1;

        // 마지막 페이지가 전체 페이지보다 클 경우 통제
        lastRangePage = Math.min(lastRangePage, totalPages);

        // 이전 구간 시작 페이지 번호, 다음 구간 시작 페이지 번호 0으로 설정
        int prevRangeLastPage = 0, nextRangeFirstPage = 0;

        if (rangeCnt > 0) { // 이전 구간이 있을 때
            prevRangeLastPage = firstRangePage - 1;
        }

        // 마지막 페이지 구간
        int lastRangeCnt = (totalPages - 1) / ranges;

        if (rangeCnt < lastRangeCnt) {
            nextRangeFirstPage = (rangeCnt + 1) * ranges + 1;
        }

        /* 쿼리스트링 값 처리 S */
        String qs = request.getQueryString();
        baseUrl = "?";

        if (StringUtils.hasText(qs)) {
            baseUrl += Arrays.stream(qs.split("&")) // & 기준으로 나누기
                    .filter(s -> !s.contains("page="))
                    .collect(Collectors.joining("&")) + "&";
        }
        baseUrl += "page=";
        /* 쿼리스트링 값 처리 E */

        this.page = page;
        this.ranges = ranges;
        this.limit = limit;
        this.total = total;
        this.totalPages = totalPages;
        this.firstRangePage = firstRangePage;
        this.lastRangePage = lastRangePage;
        this.prevRangeLastPage = prevRangeLastPage;
        this.nextRangeFirstPage = nextRangeFirstPage;
    }

    // String 배열의 0번째 - 페이지 번호 숫자, 1번째 - 페이지 URL
    public List<String[]> getPages() {

        if (total == 0) {
            return Collections.EMPTY_LIST;
        }

        List<String[]> pages = new ArrayList<>();

        for (int i = firstRangePage; i <= lastRangePage; i++) {
            String url = baseUrl + i;
            pages.add(new String[] {"" + i, url});
        }

        return pages;
    }
}
