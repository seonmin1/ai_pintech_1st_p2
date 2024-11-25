package org.koreait.pokemon.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.pokemon.api.entities.ApiResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootTest
public class ApiTest1 {

    // 매번 RestTemplate 객체를 생성할 필요 없이 생성자 정의
    private RestTemplate tpl;

    @BeforeEach
    void init() {
        tpl = new RestTemplate();
    }

    @Test
    void requestTest1() {
        String url = "https://pokeapi.co/api/v2/pokemon"; // 포켓몬 목록 URL

        ApiResponse response = tpl.getForObject(URI.create(url), ApiResponse.class); // 주소와 반환받을 자료형 입력

        System.out.println(response);
    }

    @Test
    void requestTest2() {
        String url = "https://pokeapi.co/api/v2/pokemon/1";

        String response = tpl.getForObject(URI.create(url), String.class);
        System.out.println(response);
    }
}
