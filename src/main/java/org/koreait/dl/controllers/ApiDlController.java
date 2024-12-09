package org.koreait.dl.controllers;

import org.koreait.dl.entities.TrainItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 머신러닝 컨트롤러
 */
@RestController
@RequestMapping("/api/dl")
public class ApiDlController {

    // 데이터를 파이썬으로 보내기 위한 메서드
    @GetMapping("/data")
    public List<TrainItem> sendData() {
        Random random = new Random();
        List<TrainItem> items = IntStream.range(0, 1000)
                .mapToObj(i -> TrainItem.builder()
                        .item1(random.nextInt())
                        .item2(random.nextInt())
                        .item3(random.nextInt())
                        .item4(random.nextInt())
                        .item5(random.nextInt())
                        .item6(random.nextInt())
                        .item7(random.nextInt())
                        .item8(random.nextInt())
                        .item9(random.nextInt())
                        .item10(random.nextInt())
                        .result(random.nextInt(4)) // 0~4 사이만 반복
                        .build()
                ).toList();

        return items;
    }
}
