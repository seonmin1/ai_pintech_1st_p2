package org.koreait.pokemon.services;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.koreait.global.paging.ListData;
import org.koreait.pokemon.controllers.PokemonSearch;
import org.koreait.pokemon.entities.Pokemon;
import org.koreait.pokemon.entities.QPokemon;
import org.koreait.pokemon.exceptions.PokemonNotFoundException;
import org.koreait.pokemon.repositories.PokemonRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static org.springframework.data.domain.Sort.Order.desc;

@Lazy
@Service
@RequiredArgsConstructor
public class PokemonInfoService {

    private final PokemonRepository pokemonRepository;

    // 포켓몬 목록 조회
    public ListData<Pokemon> getList(PokemonSearch search) {

        // page 번호와 1 중 큰 숫자 반환 - page 번호는 0 또는 음수가 될 수 없음
        int page = Math.max(search.getPage(), 1);

        // 한페이지 당 레코드 갯수
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        QPokemon pokemon = QPokemon.pokemon;

        /* 검색 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        String skey = search.getSkey();

        if (StringUtils.hasText(skey)) { // 키워드 검색 - 검색조건추가
            andBuilder.and(pokemon.name
                    .concat(pokemon.nameEn)
                    .concat(pokemon.flavorText).contains(skey));
        }

        /* 검색 처리 E */
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("seq")));

        Page<Pokemon> data = pokemonRepository.findAll(andBuilder, pageable);

        return null;
    }

    // 포켓몬 단일 조회
    public Pokemon get(Long seq) {

        // 등록번호로 찾고 없으면 예외처리
        Pokemon item = pokemonRepository.findById(seq).orElseThrow(PokemonNotFoundException::new);

        // 추가 정보 처리
        addInfo(item);

        return item;
    }

    // 추가 정보 처리
    private void addInfo(Pokemon item) {

    }
}
