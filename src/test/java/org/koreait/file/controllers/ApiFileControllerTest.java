package org.koreait.file.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.file.entities.FileInfo;
import org.koreait.file.repositories.FileInfoRepository;
import org.koreait.file.services.FileDeleteService;
import org.koreait.file.services.FileInfoService;
import org.koreait.member.constants.Gender;
import org.koreait.member.controllers.RequestJoin;
import org.koreait.member.services.MemberUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest
// @ActiveProfiles({"default", "test"})
@AutoConfigureMockMvc // 전체 테스트 시 사용
public class ApiFileControllerTest {

    @Autowired
    private MockMvc mockMvc; // 전체 테스트 시 사용

    @Autowired
    private FileInfoRepository repository;

    @Autowired
    private MemberUpdateService updateService;

    @Autowired
    private FileInfoService infoService;

    @Autowired
    private FileDeleteService deleteService;

    @BeforeEach
    void setup() { // 단위 테스트 시 사용
        // mockMvc = MockMvcBuilders.standaloneSetup(ApiFileController.class).build(); // ApiFileController 만 생성해서 test

        /*RequestJoin form = new RequestJoin();
        form.setEmail("user01@test.org");
        form.setPassword("_aA123456");
        form.setGender(Gender.MALE);
        form.setBirthDt(LocalDate.now().minusYears(20));
        form.setName("이이름");
        form.setNickName("이이름");
        form.setZipCode("00000");
        form.setAddress("주소!");

        updateService.process(form);*/
    }

    @Test
    // @WithMockUser(username = "user01@test.org", authorities = "USER") // 테스트를 위한 가짜 로그인 데이터
    // @WithUserDetails(value = "user01@test.org", userDetailsServiceBeanName = "memberInfoService")
    void test1() throws Exception {

        // MockMultipartFile
        MockMultipartFile file1 = new MockMultipartFile("file", "test1.png", MediaType.IMAGE_PNG_VALUE, new byte[] {1, 2, 3});
        MockMultipartFile file2 = new MockMultipartFile("file", "test2.png", MediaType.IMAGE_PNG_VALUE, new byte[] {1, 2, 3});

        mockMvc.perform(multipart("/api/file/upload")
                        .file(file1)
                        .file(file2)
                        .param("gid", "testgid")
                        .param("location", "testlocation")
                        .with(csrf().asHeader()))
                .andDo(print());

        // Thread.sleep(5000); // 실행중인 쓰레드 5초 기다렸다가 실행

        /*List<FileInfo> items = repository.getList("testgid");
        for (FileInfo item : items) {
            System.out.println(item.getCreatedBy());
        }*/
    }

    @Test
    void test2() {
        //FileInfo item = infoService.get(1L);
        //System.out.println(item);

        List<FileInfo> items = infoService.getList("testgid", null, null);
        items.forEach(System.out::println);
    }

    @Test
    void test3() {
        // 단일삭제 테스트
        // FileInfo item = deleteService.delete(1L);
        // System.out.println(item);

        // 복수개 삭제 테스트
        List<FileInfo> items = deleteService.deletes("testgid", "testlocation");
        items.forEach(System.out::println);
    }
}
