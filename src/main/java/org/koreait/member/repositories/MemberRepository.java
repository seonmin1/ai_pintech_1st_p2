package org.koreait.member.repositories;

import org.koreait.member.entities.Member;
import org.koreait.member.entities.QMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

/**
 * Member DB 저장할 Repository 생성
 */
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByEmail(String email); // 회원이 없을수도 있으므로 Optional 클래스 사용

    default boolean exists(String email) {
         QMember member = QMember.member;

         return exists(member.email.eq(email));
    }
}
