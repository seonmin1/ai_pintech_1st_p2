package org.koreait.board.controllers;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.koreait.admin.board.validators.BoardValidator;
import org.koreait.board.entities.Board;
import org.koreait.board.entities.BoardData;
import org.koreait.board.services.BoardUpdateService;
import org.koreait.board.services.configs.BoardConfigInfoService;
import org.koreait.file.constants.FileStatus;
import org.koreait.file.services.FileInfoService;
import org.koreait.global.annotations.ApplyErrorPage;
import org.koreait.global.libs.Utils;
import org.koreait.member.libs.MemberUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@ApplyErrorPage
@RequestMapping("/board")
@RequiredArgsConstructor
@SessionAttributes({"commonValue"})
public class BoardController {

    private final Utils utils;
    private final MemberUtil memberUtil;
    private final BoardConfigInfoService configInfoService;
    private final FileInfoService fileInfoService;
    private final BoardValidator boardValidator;
    private final BoardUpdateService boardUpdateService;

    // 사용자별 공통 데이터 (게시판 설정 등등)
    @ModelAttribute("commonValue")
    public CommonValue commonValue() {
        return new CommonValue();
    }

    // 게시판 목록
    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid, Model model) {

        commonProcess(bid, "list", model);

        return utils.tpl("board/list");
    }

    // 게시글 보기
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "view", model);

        return utils.tpl("board/view");
    }

    // 게시판 글쓰기
    @GetMapping("/write/{bid}")
    public String write(@PathVariable("bid") String bid, @ModelAttribute RequestBoard form, Model model) {
        commonProcess(bid, "write", model);

        form.setBid(bid);
        form.setGid(UUID.randomUUID().toString());

        if (memberUtil.isLogin())  {
            form.setPoster(memberUtil.getMember().getName());
        }

        return utils.tpl("board/write");
    }

    // 게시글 수정
    @GetMapping("/edit/{seq}")
    public String edit(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "edit", model);

        return utils.tpl("board/edit");
    }

    // 글등록, 수정 처리
    @PostMapping("/save")
    public String save(@Valid RequestBoard form, Errors errors,
                       @SessionAttribute("commonValue") CommonValue commonValue, Model model) {
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "write";
        commonProcess(form.getBid(), mode, model);

        boardValidator.validate(form, errors);

        if (errors.hasErrors()) {
            String gid = form.getGid();
            form.setEditorImages(fileInfoService.getList(gid, "editor", FileStatus.ALL));
            form.setAttachFiles(fileInfoService.getList(gid, "attach", FileStatus.ALL));

            return utils.tpl("board/" + mode);
        }

        BoardData data = boardUpdateService.process(form);

        Board board = commonValue.getBoard();

        // 글작성, 수정 성공 시 글보기 또는 글목록으로 이동
        String redirectUrl = String.format("/board/%s", board.getLocationAfterWriting()
                                                             .equals("view") ? "view/" + data.getSeq() : "list/" + board.getBid());
        return "redirect:" + redirectUrl;
    }

    // 게시글 삭제
    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model,
                         @SessionAttribute("commonValue") CommonValue commonValue) {
        commonProcess(seq, "delete", model);
        Board board = commonValue.getBoard();

        return "redirect:/board/list/" + board.getBid();
    }

    // 공통 처리 1
    public void commonProcess(String bid, String mode, Model model) {
        Board board = configInfoService.get(bid);
        String pageTitle = board.getName(); // 게시판명 - 목록, 글쓰기

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        // 게시판 공통 CSS, JS
        addScript.add("board/common");
        addCss.add("board/style");

        // 게시판 스킨별 CSS, JS
        addScript.add(String.format("board/%s/common", board.getSkin()));
        addCss.add(String.format("board/%s/style", board.getSkin()));

        if (mode.equals("write") || mode.equals("edit")) { // 글작성, 글수정
            if (board.isUseEditor()) { // 에디터를 사용하는 경우
                addCommonScript.add("ckeditor5/ckeditor");
            } else { // 에디터를 사용하지 않는 경우는 이미지 첨부 불가
                board.setUseEditorImage(false);
            }

            // 파일 업로드가 필요한 설정일 경우
            if (board.isUseAttachFile() || board.isUseEditorImage()) {
                addCommonScript.add("fileManager");
            }

            addScript.add(String.format("board/%s/form", board.getSkin()));
        }

        CommonValue commonValue = commonValue();
        commonValue.setBoard(board);

        model.addAttribute("board", board);
        model.addAttribute("commonValue", commonValue);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
        model.addAttribute("pageTitle", pageTitle);
    }

    // 공통 처리 2
    private void commonProcess(Long seq, String mode, Model model) {
        String bid = null;

        commonProcess(bid, mode, model);
    }

    // 내부 클래스 정의
    @Data
    static class CommonValue implements Serializable {
        private Board board;
    }
}
