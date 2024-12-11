package org.koreait.dl.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 예측 기능
 */
@Lazy
@Service
@Profile("dl")
public class PredictService {

    @Value("${python.run.path}")
    private String runPath;

    @Value("${python.script.path}")
    private String scriptPath;

    @Value("${python.data.url}")
    private String dataUrl;

    @Autowired
    private ObjectMapper om;

    public int[] predict(List<int[]> items) {
        try {
            String data = om.writeValueAsString(items);

            ProcessBuilder builder = new ProcessBuilder(runPath, scriptPath + "predict.py", dataUrl + "?mode=ALL", data);

            Process process = builder.start();

            InputStream in = process.getInputStream();

            return om.readValue(in.readAllBytes(), int[].class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
