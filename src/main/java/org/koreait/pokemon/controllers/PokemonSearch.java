package org.koreait.pokemon.controllers;

import lombok.Data;
import org.koreait.global.paging.CommonSearch;

import java.util.List;

/**
 * 포켓몬 도감 상세 검색 항목
 */
@Data
public class PokemonSearch extends CommonSearch {
    private List<Long> seq;
}
