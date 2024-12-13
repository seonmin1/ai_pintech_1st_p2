package org.koreait.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing // entity 변화 자동 감지를 위한 애노테이션
@EnableScheduling // 주기적으로 스케줄링 하기 위한 애노테이션
@EnableRedisHttpSession // redis 설정 자동화, 같은 로그인 정보 공유 = 같은 세션 공유
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
     * PATCH, PUT, DELETE 등등 다른 메서드를 사용할 때 정의
     * PATCH 메서드로 요청을 보내는 경우
     * <form method='POST' ...>
     *     <input type='hidden' name='_method' value='PATCH'>
     * </form>
     * - method 방식은 get, post 만 가능
     * - 다른 방식 사용하기 위해서는 hidden 타입 선언해야함
     * - 이때 방식은 post, form 양식 사용 필수!
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
