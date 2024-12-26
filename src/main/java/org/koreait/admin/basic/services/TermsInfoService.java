package org.koreait.admin.basic.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.koreait.global.entities.CodeValue;
import org.koreait.global.entities.QCodeValue;
import org.koreait.global.entities.Terms;
import org.koreait.global.repositorise.CodeValueRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

/**
 * 약관 조회 기능
 */
@Service
@RequiredArgsConstructor
public class TermsInfoService {
    private final CodeValueRepository repository;
    private final ObjectMapper om;

    public List<Terms> getList() { // 목록 전체 조회
        QCodeValue codeValue = QCodeValue.codeValue;

        List<CodeValue> items = (List<CodeValue>) repository.findAll(codeValue.code.startsWith("term_"), Sort.by(asc("code")));

        if (items != null) {
            return items.stream().map(item -> {
                try {
                    return om.readValue(item.getValue(), Terms.class);
                } catch (JsonProcessingException e) {}

                return null;
            }).filter(terms -> terms != null).toList();
        }

        return List.of();
    }
}
