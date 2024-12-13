package org.koreait.member.test.annotations;

import org.koreait.member.MemberInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Authorities;
import org.koreait.member.entities.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 가짜 데이터 생성하여 테스트
 */
public class MockSecurityContextFactory implements WithSecurityContextFactory<MockMember> {
    @Override
    public SecurityContext createSecurityContext(MockMember annotation) {
        Member member = new Member();
        member.setSeq(annotation.seq());
        member.setEmail(annotation.email());
        member.setPassword(annotation.password());
        member.setName(annotation.name());
        member.setNickName(annotation.nickName());
        member.setBirthDt(LocalDate.now().minusYears(20L));
        member.setRequiredTerms1(true);
        member.setRequiredTerms2(true);
        member.setRequiredTerms3(true);
        member.setCredentialChangedAt(LocalDateTime.now());

        List<SimpleGrantedAuthority> authorities = Arrays.stream(annotation.authority())
                .map(a -> new SimpleGrantedAuthority(a.name()))
                .toList();

        List<Authorities> _authorities = Arrays.stream(annotation.authority())
                .map(a -> {
                    Authorities auth = new Authorities();
                    auth.setAuthority(a);
                    auth.setMember(member);
                    return auth;
                }).toList();
        member.setAuthorities(_authorities);

        MemberInfo memberInfo = MemberInfo
                .builder()
                .email(annotation.email())
                .password(annotation.password())
                .member(member)
                .authorities(authorities)
                .build();

        // Authority 구현 객체
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberInfo, annotation.password(), authorities);

        // 새로운 context 객체 생성
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // 로그인 처리
        // 이미 객체가 생성되어 있으면(SecurityContextHolder) get 을 통해 값을 가져오지만, 객체가 생성되지 않았으므로 set 을 통해 생성해서 사용함
        context.setAuthentication(token);

        return context;
    }
}
