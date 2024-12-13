package org.koreait.global.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 파일 경로 설정 (application.yml)
 * - WebMvcConfigurer 인터페이스가 가지고 있는 메서드 구현
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileConfig implements WebMvcConfigurer {

    private final FileProperties properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(properties.getUrl() + "**") // uploads 파일, ** : uploads 포함한 하위 경로 전체
                .addResourceLocations("file:///" + properties.getPath());
    }
}
