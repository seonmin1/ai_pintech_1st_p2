package org.koreait.pokemon.services;

import org.junit.jupiter.api.Test;
import org.koreait.global.paging.ListData;
import org.koreait.pokemon.controllers.PokemonSearch;
import org.koreait.pokemon.entities.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PokemonInfoServiceTest {

    @Autowired
    private PokemonInfoService infoService;

    @Test
    void test1() {
        // 포켓몬 단일 조회 테스트
        // Pokemon item = infoService.get(1L);
        // System.out.println(item);

        PokemonSearch search = new PokemonSearch();

        // 검색 조회 테스트
        search.setSkey("나무줄기");

        // 포켓몬 목록 조회 테스트
        ListData<Pokemon> items = infoService.getList(search);
        items.getItems().forEach(System.out::println);
    }
}
