package org.koreait.member.services;

import lombok.RequiredArgsConstructor;
import org.koreait.member.constants.Authority;
import org.koreait.member.controllers.RequestJoin;
import org.koreait.member.entities.Authorities;
import org.koreait.member.entities.Member;
import org.koreait.member.entities.QAuthorities;
import org.koreait.member.repositories.AuthoritiesRepository;
import org.koreait.member.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Lazy // 지연로딩 - 최초로 빈을 사용할 때 생성
@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    // 객체 생성
    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    /**
     * 커맨드 객체의 타입에 따라서
     * RequestJoin 이면 회원 가입 처리, RequestProfile 이면 회원 정보 수정 처리
     */
    public void process(RequestJoin form) {
        // 커맨드 객체에서 엔티티 객체로 데이터 옮기기
        // modelMapper 사용하여 setter, getter 치환
        Member member = modelMapper.map(form, Member.class);

        // 선택 약관을 약관 항목1||약관 항목2||... 형태로 가공
        List<String> optionalTerms = form.getOptionalTerms();
        if (optionalTerms != null) {
            String _optionalTerms = optionalTerms.stream().collect(Collectors.joining("||"));
            member.setOptionalTerms(_optionalTerms);
        }

        // 비밀번호 해시화 - BCrypt 사용
        String hash = passwordEncoder.encode(form.getPassword());
        member.setPassword(hash);

        // 회원 권한
        Authorities auth = new Authorities();
        auth.setMember(member);
        auth.setAuthority(Authority.USER); // 회원 권한이 없는 경우 회원가입 시 기본 권한 USER

        save(member, List.of(auth)); // 회원 저장 처리
    }

    /**
     * 회원 정보 추가 또는 수정 처리 (공통 처리 부분)
     */
    private void save(Member member, List<Authorities> authorities) { // 기존 자료 새로 갱신

        memberRepository.saveAndFlush(member);

        /* 회원 권한 업데이트 처리 S */
        if (authorities != null) {
           // 기존 권한을 삭제하고 다시 등록
            QAuthorities qAuthorities = QAuthorities.authorities;
            List<Authorities> items = (List<Authorities>) authoritiesRepository.findAll(qAuthorities.member.eq(member));
            if (items != null) {
                authoritiesRepository.deleteAll(items);
            }
            authoritiesRepository.saveAllAndFlush(authorities);
        }

        /* 회원 권한 업데이트 처리 E */
    }

}
