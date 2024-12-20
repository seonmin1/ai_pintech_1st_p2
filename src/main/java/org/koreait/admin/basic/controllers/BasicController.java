package org.koreait.admin.basic.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.menu.MenuDetail;
import org.koreait.admin.global.menu.Menus;
import org.koreait.global.annotations.ApplyErrorPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 기본 관리자 설정 페이지
 */
@Controller
@ApplyErrorPage
@RequiredArgsConstructor
@RequestMapping("/admin/basic")
public class BasicController {

    @ModelAttribute("menuCode")
    public String menuCode() {
        return "basic";
    }

    @ModelAttribute("submenus")
    public List<MenuDetail> submenus() {
        return Menus.getMenus(menuCode());
    }

    @GetMapping({"", "/siteConfig"})
    public String siteConfig(Model model) {
        commonProcess("siteConfig", model);

        return "admin/basic/siteConfig";
    }

    /**
     * 기본설정 공통 처리 부분
     */
    private void commonProcess(String mode, Model model) {

        model.addAttribute("subMenuCode", mode);
    }
}
