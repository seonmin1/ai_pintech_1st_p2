package org.koreait.mypage.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.global.libs.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 마이페이지 인가 설정
 */
@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final Utils utils;

    // 마이페이지의 메인페이지
    @GetMapping // RequestMapping 주소와 동일하다는 의미
    public String index() {
        return utils.tpl("mypage/index");
    }
}
