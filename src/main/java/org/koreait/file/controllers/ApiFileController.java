package org.koreait.file.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.file.constants.FileStatus;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.services.*;
import org.koreait.global.exceptions.BadRequestException;
import org.koreait.global.libs.Utils;
import org.koreait.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

/**
 * 커맨드 객체 메세지 검증 처리
 */
@Tag(name="파일 API", description = "파일 업로드, 조회, 다운로드, 삭제 기능 제공합니다.")
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class ApiFileController {

    private final Utils utils;
    private final FileUploadService uploadService;
    private final FileDownloadService downloadService;
    private final FileInfoService infoService;
    private final FileDeleteService deleteService;
    private final FileDoneService doneService;
    private final ThumbnailService thumbnailService;

    // 파일 업로드
    @Operation(summary = "파일 업로드 처리") // summary - 내용기입
    @ApiResponse(responseCode = "201", // responseCode - 응답코드 명시
            description = "파일 업로드 처리, 업로드 성공시에는 업로드 완료된 파일 목록을 반환한다. 요청시 반드시 요청헤더에 multipart/form-data 형식으로 전송")
    @Parameters({
            @Parameter(name = "gid", description = "파일 그룹 ID", required = true), // 필수
            @Parameter(name = "location", description = "파일 그룹 내에서 위치 코드"),
            @Parameter(name = "file", description = "업로드 파일, 복수개 전송 가능", required = true) // 필수
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload")
    public JSONData upload(@RequestPart("file") MultipartFile[] files, @Valid RequestUpload form, Errors errors) { // 형식을 JSONData로 통일

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        form.setFiles(files);

        // 단일 파일 업로드 - 기 업로드 된 파일을 삭제하고 새로 추가
        // 순환참조 문제(FileInfoService) 발생가능성으로 FileUpdateService 대신 ApiFileController 쪽에 추가
        if (form.isSingle()) {
            deleteService.deletes(form.getGid(), form.getLocation());
        }

        List<FileInfo> uploadedFiles = uploadService.upload(form);

        // 업로드 완료 하자마자 완료 처리
        if (form.isDone()) {
            doneService.process(form.getGid(), form.getLocation());
        }

        JSONData data = new JSONData(uploadedFiles);
        data.setStatus(HttpStatus.CREATED);

        return data;
    }


    // 파일 다운로드
    @GetMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq) { // @PathVariable : 경로변수 설정
        downloadService.process(seq);
    }

    // 파일 조회 - 단일 조회
    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable("seq") Long seq) {
        FileInfo item = infoService.get(seq);

        return new JSONData(item);
    }

    // 파일 목록 조회 - gid, location
    @GetMapping({"/list/{gid}", "/list/{gid}/{location}"})
    public JSONData list(@PathVariable("gid") String gid, // gid - 필수, location - 필수아님
                         @PathVariable(name = "location", required = false) String location,
                         @RequestParam(name = "status", defaultValue = "DONE") FileStatus status) { // 완료된 파일만 보이게 설정

        List<FileInfo> items = infoService.getList(gid, location, status);

        return new JSONData(items);
    }

    // 파일 삭제 - 단일 삭제
    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {
        FileInfo item = deleteService.delete(seq);

        return new JSONData(item);
    }

    // 파일 삭제 - 복수개 삭제
    @DeleteMapping({"/deletes/{gid}", "/deletes/{gid}/{location}"})
    public JSONData deletes(@PathVariable("gid") String gid, // gid - 필수, location - 필수아님
                            @PathVariable(name = "location", required = false) String location) {

        List<FileInfo> items = deleteService.deletes(gid, location);

        return new JSONData(items);
    }

    @GetMapping("/thumb")
    public void thumb(RequestThumb form, HttpServletResponse response) {
        String path = thumbnailService.create(form); // 이미 있으면 생성 X

        if (!StringUtils.hasText(path)) {
            return;
        }

        File file = new File(path);

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            String contentType = Files.probeContentType(file.toPath());
            response.setContentType(contentType);

            OutputStream out = response.getOutputStream();
            out.write(bis.readAllBytes());

        } catch (IOException e) {}
    }
}
