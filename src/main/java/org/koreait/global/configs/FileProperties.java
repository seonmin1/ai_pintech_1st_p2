package org.koreait.global.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 파일 경로 설정
 */
@Data
@ConfigurationProperties(prefix = "file.upload")
public class FileProperties {
    private String path;
    private String url;
}
