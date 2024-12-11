package org.koreait.pokemon.tests;

import org.junit.jupiter.api.Test;
import org.koreait.pokemon.api.services.ApiUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 실제 DB에 추가(저장)하는 테스트이므로 환경변수는 포켓몬쪽과 동일하게 설정해줘야 함!
 */
@SpringBootTest
public class ApiTest2 {
    @Autowired
    private ApiUpdateService service;

    @Test
    void updateTest1() {
        service.update(3); // 100개
        service.update(4);
    }
}
