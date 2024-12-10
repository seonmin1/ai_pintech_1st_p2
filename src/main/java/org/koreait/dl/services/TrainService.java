package org.koreait.dl.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 훈련 기능
 */
@Lazy
@Service
@Profile("dl") // dl 활성화 되었을때만 선별적으로 생성 - 빈의 생성 통제
public class TrainService {

    @Value("${python.run.path}")
    private String runPath;

    @Value("${python.script.path}")
    private String scriptPath;

    @Value("${python.data.url}")
    private String dataUrl;

    @Scheduled(cron = "0 0 1 * * *") // 새벽 1시 마다 주기적으로 실행, 훈련
    public void process() {
        try {
            ProcessBuilder builder = new ProcessBuilder(runPath, scriptPath + "train.py", dataUrl);

            Process process = builder.start();
            int exitCode = process.waitFor();

        } catch (Exception e) {}
    }
}
