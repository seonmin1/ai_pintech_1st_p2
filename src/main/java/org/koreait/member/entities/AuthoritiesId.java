package org.koreait.member.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.koreait.member.constants.Authority;

/**
 * 복합키 생성을 위한 @IdClass
 */
@EqualsAndHashCode
@AllArgsConstructor
public class AuthoritiesId {
    private Member member;
    private Authority authority;
}
