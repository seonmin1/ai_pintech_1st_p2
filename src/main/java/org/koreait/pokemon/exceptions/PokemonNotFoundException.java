package org.koreait.pokemon.exceptions;

import org.koreait.global.exceptions.scripts.AlertBackException;
import org.springframework.http.HttpStatus;

/**
 * 포켓몬 검색 못할 경우 예외처리 - 메인페이지로 돌아가기
 */
public class PokemonNotFoundException extends AlertBackException {

    public PokemonNotFoundException() {
        super("NotFound.pokemon", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
