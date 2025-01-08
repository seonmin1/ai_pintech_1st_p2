package org.koreait.board.services.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.board.controllers.RequestBoard;
import org.koreait.board.entities.Board;
import org.koreait.board.repositories.BoardRepository;
import org.koreait.global.exceptions.scripts.AlertException;
import org.koreait.global.libs.Utils;
import org.koreait.member.constants.Authority;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 설정 업데이트 서비스
 * 1. 등록
 * 2. 수정
 */
@Lazy
@Service
@RequiredArgsConstructor
public class BoardConfigUpdateService {
    private final BoardRepository boardRepository;
    private final Utils utils;

    public void process(RequestBoard form)  {
        String bid = form.getBid();
        Board board = boardRepository.findById(bid).orElseGet(Board::new);

        board.setBid(bid);
        board.setName(form.getName());
        board.setOpen(form.isOpen());
        board.setCategory(form.getCategory());
        board.setRowsPerPage(form.getRowsPerPage() < 1 ? 20 : form.getRowsPerPage()); // 1페이지당 게시글 최소 20개
        board.setPageRanges(form.getPageRanges() < 1 ? 10 : form.getPageRanges()); // 기본값 10
        board.setPageRangesMobile(form.getPageRangesMobile() < 1 ? 5 : form.getPageRangesMobile()); // 기본값 5
        board.setUseEditor(form.isUseEditor());
        board.setUseEditorImage(form.isUseEditorImage());
        board.setUseAttachFile(form.isUseAttachFile());
        board.setUseComment(form.isUseComment());
        board.setSkin(StringUtils.hasText(form.getSkin()) ? form.getSkin() : "default");
        board.setListAuthority(Objects.requireNonNullElse(form.getListAuthority(), Authority.ALL));
        board.setViewAuthority(Objects.requireNonNullElse(form.getViewAuthority(), Authority.ALL));
        board.setWriteAuthority(Objects.requireNonNullElse(form.getWriteAuthority(), Authority.ALL));
        board.setCommentAuthority(Objects.requireNonNullElse(form.getCommentAuthority(), Authority.ALL));

        String locationAfterWriting = form.getLocationAfterWriting();
        board.setLocationAfterWriting(StringUtils.hasText(locationAfterWriting) ? locationAfterWriting : "list");

        boardRepository.saveAndFlush(board);
    }

    // 게시판 설정 목록 수정, 삭제 처리
    public void process(List<Integer> chks, String mode) {
        mode = StringUtils.hasText(mode) ? mode : "edit";
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("처리할 게시판을 선택하세요.");
        }

        List<Board> items = new ArrayList<>();
        for (int chk : chks) {
            String bid = utils.getParam("bid_" + chk);

            if (mode.equals("delete")) {
                boardRepository.deleteById(bid);
                continue;
            }

            Board item = boardRepository.findById(bid).orElse(null);
            if (item == null) continue;

            item.setName(utils.getParam("name_" + chk));
            item.setOpen(Boolean.parseBoolean(utils.getParam("open_" + chk)));
            item.setSkin(utils.getParam("skin_" + chk));
            items.add(item);
        }

        if (!items.isEmpty()) {
            boardRepository.saveAll(items);
        }

        boardRepository.flush();
    }
}
