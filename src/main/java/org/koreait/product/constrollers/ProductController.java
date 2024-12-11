package org.koreait.product.constrollers;

import lombok.RequiredArgsConstructor;
import org.koreait.global.annotations.ApplyErrorPage;
import org.koreait.product.entities.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@ApplyErrorPage
@RequiredArgsConstructor
public class ProductController {

    @GetMapping("/product")
    public String news(Model model) {

        List<Product> productList = new ArrayList();

        productList.add(new Product("포켓몬 카드 게임×환상의 포켓몬 GET 대작전!","카드 게임 2024년 11월 22일","https://data1.pokemonkorea.co.kr/newdata/2024/11/2024-11-22_18-30-28-22148-1732267828.png","https://pokemonkorea.co.kr/news/2/15458?cate=0&sword="));
        productList.add(new Product("스칼렛&바이올렛 확장팩 「초전브레이커」11월 대발매!"," 카드 게임 2024년 11월 19일","https://data1.pokemonkorea.co.kr/newdata/2024/11/2024-11-19_11-49-19-86393-1731984559.png","https://pokemoncard.co.kr/card/675"));
        productList.add(new Product("2025 코리안리그 시즌1 대회 참가 안내"," 카드 게임 2024년 11월 01일","https://data1.pokemonkorea.co.kr/newdata/2024/11/2024-11-01_15-15-57-14985-1730441757.jpg","https://pokemonkorea.co.kr/news/2/15030?cate=0&sword="));
        productList.add(new Product("강화 확장팩 「낙원드래고나」 발매 기념 실드전 개최!"," 카드 게임 2024년 10월 25일","https://data1.pokemonkorea.co.kr/newdata/2024/10/2024-10-25_19-26-09-60819-1729851969.png","https://www.pokemonkorea.co.kr/PCG_Sealed_battle_SV7a"));
        productList.add(new Product("포켓몬 카드 게임 온라인 공식 인증샵 프로모 카드 증정 이벤트!"," 카드 게임 2024년 10월 30일","https://data1.pokemonkorea.co.kr/newdata/2024/10/2024-10-25_16-42-59-62431-1729842179.png","https://pokemonkorea.co.kr/news/2/14980?cate=0&sword="));
        productList.add(new Product("[2025 포켓몬 카드샵 대항전] 대표 선발전 개최!"," 카드 게임 2024년 10월 24일","https://data1.pokemonkorea.co.kr/newdata/2024/10/2024-10-24_11-37-23-22313-1729737443.png","https://pokemonkorea.co.kr/2025card_shop_league_a/menu516"));
        productList.add(new Product("게임도감을 완성하고 색이 다른 「메로엣타」를 손에 넣자!"," 게임 2024년 10월 18일","https://data1.pokemonkorea.co.kr/newdata/2024/10/2024-10-18_14-26-14-24714-1729229174.png","https://pokemonkorea.co.kr/2025card_shop_league_a/menu516"));
        productList.add(new Product("스칼렛&바이올렛 강화 확장팩 「낙원드래고나」10월 대발매!"," 카드 게임 2024년 10월 11일","https://data1.pokemonkorea.co.kr/newdata/2024/10/2024-10-11_15-45-00-66235-1728629100.png","https://pokemoncard.co.kr/card/668"));

        model.addAttribute("productList", productList);

        return "front/product/product";
    }

}
