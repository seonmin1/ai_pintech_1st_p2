package org.koreait.pokemon.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;

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
}
