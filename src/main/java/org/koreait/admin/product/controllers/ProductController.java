package org.koreait.admin.product.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.admin.global.menu.SubMenus;
import org.koreait.file.constants.FileStatus;
import org.koreait.file.services.FileInfoService;
import org.koreait.global.annotations.ApplyErrorPage;
import org.koreait.global.libs.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 포켓몬 홈페이지 - 상품 쇼핑몰
 */
@ApplyErrorPage
@RequiredArgsConstructor
@Controller("adminProductController") // 이름 변경
@RequestMapping("/admin/product") // 경로 설정
public class ProductController implements SubMenus {

    private final Utils utils;
    private final FileInfoService fileInfoService;

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
    public String add(@ModelAttribute RequestProduct form, Model model) {
        commonProcess("add", model);

        form.setGid(UUID.randomUUID().toString());

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
    public String save(@Valid RequestProduct form, Errors errors, Model model) {
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        commonProcess(mode, model);

        if (errors.hasErrors()) { // 검증실패
            String gid = form.getGid();
            form.setMainImages(fileInfoService.getList(gid, "main", FileStatus.ALL)); // 미완료된 파일도 함께 조회
            form.setListImages(fileInfoService.getList(gid, "list", FileStatus.ALL));
            form.setEditorImages(fileInfoService.getList(gid, "editor", FileStatus.ALL));

            return "admin/product/" + mode;
        }

        // 상품 등록, 수정 처리 서비스

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
        mode = StringUtils.hasText(mode) ? mode : "list";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        String pageTitle = "";

        if (mode.equals("list")) {
            pageTitle = "상품목록";

        } else if (mode.equals("add") || mode.equals("edit")) {
            pageTitle = mode.equals("edit") ? "상품수정" : "상품등록";
            addCommonScript.add("fileManager");
            addCommonScript.add("ckeditor5/ckeditor");
            addScript.add("product/product");

        } else if (mode.equals("category")) {
            pageTitle = "분류관리";

        } else if (mode.equals("delivery")) {
            pageTitle = "배송정책관리";

        }

        pageTitle += " - 상품관리";

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("subMenuCode", mode);
    }
}
