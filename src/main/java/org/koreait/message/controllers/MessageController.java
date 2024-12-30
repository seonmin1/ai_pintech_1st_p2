package org.koreait.message.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.global.annotations.ApplyErrorPage;
import org.koreait.global.libs.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 쪽지보내기 기능 - 회원 / 관리자
 */
@Controller
@ApplyErrorPage // 에러페이지 표시
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final Utils utils;

    // 쪽지 작성 양식
    @GetMapping
    public String form() {

        return utils.tpl("message/form");
    }

    // 쪽지 작성 - 작성 후 보낸 쪽지 목록으로 이동
    @PostMapping
    public String process() {

        return "redirect:/message/list";
    }

    // 보내거나 받은 쪽지 목록 - 받은 쪽지 목록 default
    @GetMapping("/list")
    public String list() {

        return utils.tpl("message/list");
    }

    // 쪽지 개별 조회
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq) {

        return utils.tpl("message/view");
    }
}
