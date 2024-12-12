package org.koreait.wishlist.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.member.entities.Member;
import org.koreait.wishlist.constants.WishType;

/**
 * 3가지 더해서 복합키 생성
 */
@Data
@Entity
@IdClass(WishId.class)
public class Wish {
    @Id
    private Long seq;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 15, name = "_type") // type 자체는 예약어이므로 이름 _type 으로 변경
    private WishType type;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
