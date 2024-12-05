package org.koreait.file.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.services.FileUploadService;
import org.koreait.global.exceptions.BadRequestException;
import org.koreait.global.libs.Utils;
import org.koreait.global.rests.JSONData;
import org.koreait.member.controllers.RequestLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 커맨드 객체 메세지 검증 처리
 */
@Tag(name="파일 API", description = "파일 업로드, 조회, 다운로드, 삭제 기능 제공합니다.")
@RestController
@RequestMapping("/api/file")
@NoArgsConstructor
public class ApiFileController {

    @Autowired
    private Utils utils;

    @Autowired
    private FileUploadService uploadService;

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

        List<FileInfo> uploadedFiles = uploadService.upload(form);
        JSONData data = new JSONData(uploadedFiles);
        data.setStatus(HttpStatus.CREATED);

        return data;
    }


    // 파일 다운로드
    @GetMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq) { // @PathVariable : 경로변수 설정

    }

    // 파일 조회 - 단일 조회
    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable("seq") Long seq) {

        return null;
    }

    // 파일 목록 조회 - gid, location
    @GetMapping({"/list/{gid}", "/list/{gid}/{location}"})
    public JSONData list(@PathVariable("gid") String gid, // gid - 필수, location - 필수아님
                         @PathVariable(name = "location", required = false) String location) {

        return null;
    }

    // 파일 삭제 - 단일 삭제
    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {

        return null;
    }

    // 파일 삭제 - 복수개 삭제
    @DeleteMapping({"/deletes/{gid}", "/deletes/{gid}/{location}"})
    public JSONData deletes(@PathVariable("gid") String gid, // gid - 필수, location - 필수아님
                            @PathVariable(name = "location", required = false) String location) {

        return null;
    }
}
