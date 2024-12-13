package org.koreait.global.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 회원이 가진 공통 속성
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 변화감지를 통해 값을 자동으로 넣어줌
public abstract class BaseMemberEntity extends BaseEntity {

    @CreatedBy
    @Column(length = 65, updatable = false) // 글자수, 수정불가 설정
    private String createdBy;

    @LastModifiedBy
    @Column(length = 65, insertable = false) // 글자수 설정, update & insert 쿼리가 들어오는 것을 막음
    private String modifiedBy;
}
