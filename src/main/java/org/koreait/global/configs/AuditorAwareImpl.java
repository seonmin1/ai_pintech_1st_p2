package org.koreait.global.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.member.libs.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 로그인한 회원 정보 DB에 자동 저장
 */
@Lazy
@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final MemberUtil memberUtil;

    @Override
    public Optional<String> getCurrentAuditor() {
        String email = null; // 기본값 null로 설정
        if (memberUtil.isLogin()) { // 로그인 상태일 때 이메일 가져오기
            email = memberUtil.getMember().getEmail();
        }

        return Optional.ofNullable(email);
    }
}
