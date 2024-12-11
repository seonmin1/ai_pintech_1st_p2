package org.koreait.file.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash(value = "test_hash", timeToLive = 300) // 시간은 초 단위로 입력 (300초: 60초 * 5)
public class RedisItem implements Serializable { // 직렬화 필수

    @Id // 스프링 데이터는 Id 필수
    private String key;
    private int price;
    private String productNm;
}
