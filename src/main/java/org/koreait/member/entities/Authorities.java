package org.koreait.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.member.constants.Authority;

import java.io.Serializable;

/**
 * Member & Authority 복합키 생성
 * - @IdClass 사용
 */
@Data
@Entity
@IdClass(AuthoritiesId.class)
public class Authorities implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length=15)
    private Authority authority;
}
