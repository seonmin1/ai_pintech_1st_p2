package org.koreait.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

/**
 * 수동 등록 빈 객체들을 모아두는 공간
 */
@Configuration
public class BeansConfig {

    @Lazy // 최초에 한번 호출했을 때 객체 생성 후 공유 - 필요할 때 한번 만들어 공유
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
