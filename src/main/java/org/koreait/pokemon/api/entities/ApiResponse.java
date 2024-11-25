package org.koreait.pokemon.api.entities;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private int count; // 포켓몬 갯수
    private String next; // 다음 주소
    private String previous;
    private List<UrlItem> results;
}
