package org.koreait.pokemon.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 알려지지 않은 오류가 있을 시 무시 - 필요한 부분만 가져와 사용 가능!
public class UrlItem {
    private String name;
    private String url;
}
