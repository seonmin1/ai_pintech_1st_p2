package org.koreait.admin.product.controllers;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.menu.SubMenus;
import org.koreait.global.annotations.ApplyErrorPage;
import org.koreait.global.libs.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 포켓몬 홈페이지 - 상품 쇼핑몰
 */
@ApplyErrorPage
@RequiredArgsConstructor
@Controller("adminProductController") // 이름 변경
@RequestMapping("/admin/product") // 경로 설정
public class ProductController implements SubMenus {

    private final Utils utils;

    @Override
    @ModelAttribute("menuCode")
    public String menuCode() {
        return "product";
    }

    // 상품 목록
    // 삭제 메뉴 추가하지 않고 목록쪽에서 처리
    @GetMapping({"", "list"})
    public String list(Model model) {
        commonProcess("list", model);

        return "admin/product/list";
    }

    //상품 등록
    @GetMapping("/add")
    public String add(Model model) {
        commonProcess("add", model);

        return "admin/product/add";
    }

    // 상품 정보 수정
    @GetMapping("/edit/{seq}")
    public String edit(@PathVariable("seq") Long seq, Model model) {
        commonProcess("edit", model);

        return "admin/product/edit";
    }

    // 상품 등록, 수정 처리
    @PostMapping("/save")
    public String save(Model model) {
        commonProcess("", model);

        return "redirect:/admin/product/list"; // 완료 시 목록으로 이동
    }

    // 상품 분류 목록
    @GetMapping("/category")
    public String categoryList(Model model) {
        commonProcess("category", model);

        return "admin/product/category/list";
    }

    // 분류 등록
    @GetMapping({"/category/add", "/category/edit/{cate}"})
    public String categoryUpdate(@PathVariable(name = "cate", required = false) String cate, Model model) {
        commonProcess("category", model);

        return "admin/product/category/add";
    }

    // 분류 등록, 수정 처리
    @PostMapping("/category/save")
    public String categorySave(Model model) {
        commonProcess("category", model);

        return "redirect:/admin/product/category";
    }

    // 배송 정책 관리
    @GetMapping("/delivery")
    public String delivery(Model model) {
        commonProcess("delivery", model);

        return "admin/product/delivery";
    }

    // 공통 처리 부분
    private void commonProcess(String mode, Model model) {
        model.addAttribute("subMenuCode", mode);
    }
}
