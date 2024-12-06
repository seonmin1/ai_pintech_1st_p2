package org.koreait.file.services;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.exceptions.FileNotFoundException;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * HttpServletResponse, 응답헤더
 * "Content-Disposition" 사용하여 파일 타입 지정 후 다운로드 설정
 */
@Lazy
@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final FileInfoService infoService;
    private final HttpServletResponse response;

    public void process(Long seq) {

        FileInfo item = infoService.get(seq);

        String filePath = item.getFilePath();
        String fileName = item.getFileName();

        // 문자(3바이트) 출력 시 깨질 위험이 있으므로 2바이트 형태의 유니코드로 변환 - ISO_8859_1
        fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);

        // contentType 없을 경우 기본값 설정 - "application/octet-stream"
        String contentType = item.getContentType();
        contentType = StringUtils.hasText(contentType) ? contentType : "application/octet-stream";

        File file = new File(item.getFilePath());

        if (!file.exists()) { // 파일이 없을 경우 예외처리
            throw new FileNotFoundException();
        }

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            // 바디의 출력을 filename에 지정된 파일로 변경
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            // contentType 설정
            response.setContentType(contentType);

            // cache 설정
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache"); // 옛날 모델

            // 만료시간 0으로 설정 - 기본(설정한) 만료시간이 지나면 브라우저가 꺼지므로 다운로드 시 만료시간이 없도록
            response.setIntHeader("Expires", 0);

            response.setContentLengthLong(file.length());

            OutputStream out = response.getOutputStream();
            out.write(bis.readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
