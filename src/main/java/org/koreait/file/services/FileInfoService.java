package org.koreait.file.services;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.file.constants.FileStatus;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.entities.QFileInfo;
import org.koreait.file.exceptions.FileNotFoundException;
import org.koreait.file.repositories.FileInfoRepository;
import org.koreait.global.configs.FileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * FileInfoRepository
 */
@Lazy // 순환참조문제(초기로딩 늦어지는 문제 등) 방지
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileInfoService {

    private final FileInfoRepository infoRepository;
    private final FileProperties properties;
    private final HttpServletRequest request;

    public FileInfo get(Long seq) {
        FileInfo item = infoRepository.findById(seq).orElseThrow(FileNotFoundException::new);

        addInfo(item); // 추가 정보 처리

        return item;
    }

    public List<FileInfo> getList(String gid, String location, FileStatus status) {
        status = Objects.requireNonNullElse(status, FileStatus.ALL); // null 일 때 ALL

        QFileInfo fileInfo = QFileInfo.fileInfo;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(fileInfo.gid.eq(gid)); // gid - 필수

        // location 값이 있을 때 상세조회 - 선택
        if (StringUtils.hasText(location)) {
            andBuilder.and(fileInfo.location.eq(location));
        }

        // 파일 작업 완료 상태 - ALL 아닐 때 DONE 이면 True, 아니면 false
        if (status != FileStatus.ALL) {
            andBuilder.and(fileInfo.done.eq(status == FileStatus.DONE));
        }

        List<FileInfo> items = (List<FileInfo>) infoRepository.findAll(andBuilder, Sort.by(Sort.Order.asc("createAt")));

        // 추가 정보 처리
        items.forEach(this::addInfo);

        return items;
    }

    public List<FileInfo> getList(String gid, String location) {
        return getList(gid, location, FileStatus.DONE); // 완료된 파일
    }

    public List<FileInfo> getList(String gid) { // 기본동작 - 파일 그룹 작업 완료된 파일
        return getList(gid, null);
    }

    // 추가 정보 처리 - filePath, fileUrl 완성하기 위한 목적
    public void addInfo(FileInfo item) {
        // filePath - 서버에 올라간 실제 경로(다운로드, 삭제 시 활용)
        item.setFilePath(getFilePath(item));

        // fileUrl - 접근할 수 있는 주소(브라우저)
        item.setFileUrl(getFileUrl(item));
    }

    // 많이 사용하므로 별도의 메서드로 설정
    public String getFilePath(FileInfo item) {
        Long seq = item.getSeq();

        // 오류방지를 위해 null 일 때 빈 문자열로 기본값 설정
        String extension = Objects.requireNonNullElse(item.getExtension(), "");

        return String.format("%s%s/%s", properties.getPath(), getFolder(seq), seq + extension);

    }

    // 메서드 오버라이드
    public String getFilePath(Long seq) {
        FileInfo item = infoRepository.findById(seq).orElseThrow(FileNotFoundException::new);
        return getFilePath(item);
    }

    // 많이 사용하므로 별도의 메서드로 설정
    public String getFileUrl(FileInfo item) {
        Long seq = item.getSeq();

        // 오류방지를 위해 null 일 때 빈 문자열로 기본값 설정
        String extension = Objects.requireNonNullElse(item.getExtension(), "");

        return String.format("%s%s%s/%s", request.getContextPath(), properties.getUrl(), getFolder(seq), seq + extension);
    }

    // 메서드 오버라이드
    public String getFileUrl(Long seq) {
        FileInfo item = infoRepository.findById(seq).orElseThrow(FileNotFoundException::new);
        return getFileUrl(item);
    }

    private long getFolder(long seq) {
        return seq % 10L;
    }
}
