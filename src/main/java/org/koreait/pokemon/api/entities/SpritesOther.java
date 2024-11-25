package org.koreait.pokemon.api.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.Map;

@Data
public class SpritesOther {
    @JsonAlias("official-artwork") // JSON 형태로 가져올 때 별칭 설정 가능
    private Map<String, String> officialArtwork;
}
