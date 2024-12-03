package org.koreait.admin.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController {

    /**
     * 관리자 메인페이지 구현
     */
    @GetMapping
    public String index() {

        return "admin/main/index";
    }
}
