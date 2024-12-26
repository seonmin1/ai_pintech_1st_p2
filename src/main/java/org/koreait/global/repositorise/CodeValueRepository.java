package org.koreait.global.repositorise;

import org.koreait.global.entities.CodeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * 코드 저장 & 조회 공통
 */
public interface CodeValueRepository extends JpaRepository<CodeValue, String>, QuerydslPredicateExecutor<CodeValue> {
}
