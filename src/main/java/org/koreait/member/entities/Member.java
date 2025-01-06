package org.koreait.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.koreait.file.entities.FileInfo;
import org.koreait.global.entities.BaseEntity;
import org.koreait.member.constants.Gender;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Member Entity 구성
 */
@Data
@Entity
public class Member extends BaseEntity implements Serializable {
    @Id @GeneratedValue // 기본키
    private Long seq; // 회원 번호

    @Column(length = 65, nullable = false, unique = true) // 길이 설정, 값 필수, 유니크 제약조건
    private String email; // 이메일

    @Column(length = 65, nullable = false)
    private String password; // 비밀번호

    @Column(length = 40, nullable = false)
    private String name; // 이름

    @Column(length = 40, nullable = false)
    private String nickName; // 닉네임

    @Column(nullable = false)
    private LocalDate birthDt; // 생년월일

    @Enumerated(EnumType.STRING) // enum 클래스
    @Column(length = 10, nullable = false)
    private Gender gender; // 성별

    @Column(length = 10, nullable = false)
    private String zipCode; // 우편번호

    @Column(length = 100, nullable = false)
    private String address; // 주소

    @Column(length = 100)
    private String addressSub; // 나머지 주소 - 필수 X

    private boolean requiredTerms1; // 필수 약관

    private boolean requiredTerms2; // 필수 약관

    private boolean requiredTerms3; // 필수 약관

    @Column(length = 50)
    private String optionalTerms; // 선택 약관

    @JsonIgnore // 순환 참조 문제 해결 (참조 끊김)
    @ToString.Exclude // 순환 참조 배제
    @OneToMany(mappedBy = "member") // 기본 fetch 타입은 LAZY 이므로 따로 설정 X
    private List<Authorities> authorities;

    private LocalDateTime credentialChangedAt; // 비밀번호 변경 일시

    @Transient
    private FileInfo profileImage; // 프로필 이미지
}
