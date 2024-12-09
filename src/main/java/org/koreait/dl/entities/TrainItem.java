package org.koreait.dl.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.global.entities.BaseEntity;

/**
 * 학습데이터 - DB에 기록하고 학습
 */
@Data
@Entity
@Builder // 기본 생성자 private - @NoArgsConstructor 만 사용했을 때 오류 발생!
@NoArgsConstructor
@AllArgsConstructor // Builder 패턴일 때 기본 생성자가 접근 가능해야하는 경우 @NoArgsConstructor 함께 사용
public class TrainItem extends BaseEntity {

    @Id @GeneratedValue
    private Long seq;

    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;
    private int item7;
    private int item8;
    private int item9;
    private int item10;
    private int result; // 결과는 정수범위 int 한정

}
