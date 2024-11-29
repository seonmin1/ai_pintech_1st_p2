package org.koreait.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.member.constants.Authority;

/**
 * Member & Authority 복합키 생성
 * - @IdClass 사용
 */
@Data
@Entity
@IdClass(AuthoritiesId.class) // 복합키 - 기본키
public class Authorities {
    @Id
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    private Member member;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Authority authority;
}
