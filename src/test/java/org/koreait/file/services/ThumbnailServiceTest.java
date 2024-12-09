package org.koreait.file.services;

import org.junit.jupiter.api.Test;
import org.koreait.file.controllers.RequestThumb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 파일 썸네일 테스트
 */
@SpringBootTest
public class ThumbnailServiceTest {

    @Autowired
    private ThumbnailService service;

    @Test
    void thumbPathTest() {
        RequestThumb form = new RequestThumb();
        form.setSeq(1005L);
        form.setWidth(100);
        form.setHeight(100);

        String path = service.getThumbPath(1005L, null, 100, 100); // seq - DB 번호 확인 후 입력
        path = service.create(form);
        System.out.println(path);

        form.setSeq(null);
        form.setUrl("https://mimgnews.pstatic.net/image/origin/138/2024/12/09/2187658.jpg");

        String path2 = service.getThumbPath(0L, "https://mimgnews.pstatic.net/image/origin/138/2024/12/09/2187658.jpg", 100, 100);
        path2 = service.create(form);
        System.out.println(path2);
    }
}
