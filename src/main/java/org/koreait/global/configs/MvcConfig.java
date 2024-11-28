package org.koreait.global.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
public class MvcConfig implements WebMvcConfigurer {
    /**
     * 정적 경로 설정
     * CSS, js, image
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * HandlerMapping이 못찾으면 해당 경로로 유입
         * ** - 해당 클래스를 포함한 하위 패키지
         */
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
