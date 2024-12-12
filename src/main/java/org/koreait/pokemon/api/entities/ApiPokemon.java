package org.koreait.pokemon.api.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 오류가 있을 시 무시 - 필요한 부분만 가져와 사용 가능!
public class ApiPokemon {
    private int id;
    private String name;
    private Sprites sprites;
    private int weight; // 몸무게
    private int height; // 키
    private List<Types> types; // 타입
    private List<Ability> abilities; // 능력치

    @JsonAlias("base_experience") // 별칭 설정
    private int baseExperience; // 경험치

    // https://pokeapi.co/api/v2/pokemon-species/1/
    private List<Names> names;

    @JsonAlias("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;

    private List<Genus> genera;
}
