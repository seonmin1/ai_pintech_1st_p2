package org.koreait.pokemon.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;

import java.util.List;

/**
 * 래퍼클래스는 null 허용
 * 기본형은 null 허용하지 않으므로 notnull 제약조건 붙음!
 */
@Data
@Entity
public class Pokemon extends BaseEntity {
    @Id
    private Long seq;

    @Column(length = 50)
    private String name; // 포켓몬 한글 이름 (기본값)

    @Column(length = 50)
    private String nameEn; // 포켓몬 영어 이름
    private int weight;
    private int height;
    private int baseExperience;

    private String frontImage;

    @Lob
    private String flavorText; // 설명
    private String types; // 타입1||타입2|| 형태로 가공
    private String abilities; // 능력1||능력2|| 형태로 가공

    @Column(length = 100)
    private String genus; // 분류

    @Transient // DB 반영 X
    private List<String> _types; // 가공한 형태

    @Transient // DB 반영 X
    private List<String> _abilities; // 가공한 형태
}
