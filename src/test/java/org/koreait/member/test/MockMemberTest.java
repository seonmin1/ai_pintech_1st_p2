package org.koreait.member.test;

import org.junit.jupiter.api.Test;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.koreait.member.libs.MemberUtil;
import org.koreait.member.test.annotations.MockMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class MockMemberTest {

    @Autowired
    private MemberUtil memberUtil;

    @Test
    @MockMember(authority = {Authority.USER, Authority.ADMIN}) // MockMember 애노테이션에 정의한 가짜 데이터 값을 가져옴, 추가 설정 가능
    void test1() {
        Member member = memberUtil.getMember();
        System.out.println(member);
        System.out.println(memberUtil.isLogin());
        System.out.println(memberUtil.isAdmin());
    }
}
