package org.koreait.member.libs;

import jakarta.servlet.http.HttpSession;
import org.koreait.member.MemberInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberUtil {

    @Autowired
    private HttpSession session;

    /**
     * 로그인 여부 체크
     * null == 미로그인, null != 로그인 상태
     */
    public boolean isLogin() {
        return getMember() != null;
    }

    /**
     * 관리자 여부 체크
     * 권한 - MANAGER, ADMIN
     */
    public boolean isAdmin() {

        return isLogin() &&
                getMember().getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority() == Authority.ADMIN || a.getAuthority()  == Authority.MANAGER);
    }

    /**
     * 로그인 한 회원의 정보 조회
     * - SecurityContextHolder 사용하여 가져옴
     */
    public Member getMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 승인받고 getPrincipal이 MemberInfo의 구현체 일때
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof MemberInfo memberInfo) {

            Member member = (Member) session.getAttribute("member");

            if (member == null) {
                member = memberInfo.getMember();
                session.setAttribute("member", member);

                return member;
            } else {
                return member;
            }
        }

        return null;
    }
}
