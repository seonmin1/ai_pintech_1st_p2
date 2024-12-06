package org.koreait.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
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

    /**
     * PATCH, PUT, DELETE 등등
     * PATCH 메서드로 요청을 보내는 경우
     * <form method='POST' ...>
     *     <input type='hidden' name='_method' value='PATCH'>
     * </form>
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
