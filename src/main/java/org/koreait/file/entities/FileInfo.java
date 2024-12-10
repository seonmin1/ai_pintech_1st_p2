package org.koreait.file.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseMemberEntity;
import org.springframework.util.StringUtils;

/**
 * 파일 정보 - 업로드 전 정보 기록이 우선되도록 구성
 */
@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_gid", columnList = "gid, createdAt"),
        @Index(name = "idx_gid_location", columnList = "gid, location, createdAt")
}) // Index - 선택도가 낮을수록 유리, 높으면 사용하지 않는 것이 나음
public class FileInfo extends BaseMemberEntity {

    @Id @GeneratedValue // 증감 번호 등록 시 사용하는 애너테이션
    private Long seq; // 파일 등록 번호

    @Column(length = 45, nullable = false)
    private String gid; // 파일 그룹

    @Column(length = 45)
    private String location; // 그룹 내에서의 파일 위치

    @Column(length = 100, nullable = false)
    private String fileName; // 업로드 시 원 파일명

    @Column(length = 30)
    private String extension; // 확장자

    @Column(length = 65)
    private String contentType; // 파일 형식 image/png application...

    @Transient // DB에 반영 X - 내부에서 사용할 목적
    private String fileUrl; // URL로 파일 접근할 수 있는 주소 - 2차 가공

    @Transient // DB에 반영 X - 내부에서 사용할 목적
    private String filePath; // 파일이 서버에 있는 경로

    @Transient
    private String thumbUrl; // 썸네일 기본 URL

    private boolean done; // 파일과 연관된 작업이 완료되었는지 여부

    // 이미지 형식 여부 체크
    public boolean isImage() {

        return StringUtils.hasText(contentType) && contentType.contains("image/");
    }
}
