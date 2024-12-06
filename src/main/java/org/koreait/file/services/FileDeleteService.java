package org.koreait.file.services;

import lombok.RequiredArgsConstructor;
import org.koreait.file.constants.FileStatus;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.repositories.FileInfoRepository;
import org.koreait.global.exceptions.UnAuthorizedException;
import org.koreait.member.libs.MemberUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class FileDeleteService {

    private final FileInfoService infoService;
    private final FileInfoRepository infoRepository;
    private final MemberUtil memberUtil;

    public FileInfo delete(Long seq) {
        FileInfo item = infoService.get(seq);
        String filePath = item.getFilePath();

        // 0. 파일 소유자만 삭제 가능하게 통제 - 다만 관리자는 가능
        String createdBy = item.getCreatedBy();
        if (!memberUtil.isAdmin() && StringUtils.hasText(createdBy) // 관리자가 아니고 회원이 올린 파일일 때
                && (!memberUtil.isLogin() || (memberUtil.getMember().getEmail().equals(createdBy)))) { // 비회원 또는 로그인상태지만 파일 소유자가 아닐 때
            throw new UnAuthorizedException(); // 삭제권한 없음
        }

        // 1. DB에서 정보를 제거
        infoRepository.delete(item);
        infoRepository.flush();

        // 2. 파일이 서버에 존재하면 파일도 삭제
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            file.delete();
        }

        // 3. 삭제된 파일 정보를 반환
        return item;

    }

    public List<FileInfo> deletes(String gid, String location) {
        List<FileInfo> items = infoService.getList(gid, location, FileStatus.ALL);
        items.forEach(i -> delete(i.getSeq()));

        return items;
    }

    // gid만 가지고 삭제할 경우
    public List<FileInfo> deletes(String gid) {
        return deletes(gid, null);
    }
}
