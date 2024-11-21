package org.koreait.member.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.koreait.member.constants.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class RequestJoin extends RequestAgree {

    @NotNull
    private boolean[] requiredTerms; // 필수 약관 동의 여부 - 필수는 반드시 모두 선택 - 갯수 체크만으로도 충분
    private List<String> optionalTerms; // 선택 약관 동의 여부 - 선택은 어떤 약관인지를 구분할 수 있어야 함

    @Email
    @NotBlank
    private String email; // 이메일

    @NotBlank
    private String name; // 회원명

    @NotBlank
    @Size(min = 8) // 비밀번호 8자리 이상
    private String password; // 비밀번호

    @NotBlank
    private String confirmPassword; // 비밀번호 확인

    @NotBlank
    private String nickName; // 닉네임

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDt; // 생년월일

    @NotNull
    private Gender gender; // 성별 - enum 상수 정의

    @NotBlank
    private String zipCode; // 우편번호

    @NotBlank
    private String address; // 주소
    private String addressSub; // 나머지 주소
}
