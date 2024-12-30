package org.koreait.mypage.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.koreait.file.entities.FileInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.constants.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 마이페이지에서 변경가능한 요소 정의
 */
@Data
public class RequestProfile {

    private String mode;

    private String email; // 이메일

    @NotBlank
    private String name; // 회원명

    @NotBlank
    private String nickName; // 닉네임

    // @Size(min = 8)
    private String password; // 비밀번호
    private String confirmPassword; // 비밀번호 확인

    @NotNull
    private Gender gender; // 성별

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDt; // 생년월일

    @NotBlank
    private String zipCode; // 우편번호

    @NotBlank
    private String address; // 주소
    private String addressSub; // 나머지 주소

    private List<String> optionalTerms; // 추가 선택 약관

    private List<Authority> authorities; // 권한

    private FileInfo profileImage;
}
