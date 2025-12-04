package com.nutricare.model.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutricare.model.dto.RagDietContext;

// RAG 호출 전용 서비스 (이게 내가 말한 RagClient 역할)
@Service
public class RagApiServiceImpl implements RagApiService{

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String ragUrl = "http://fastapi-server:8000/rag/diet"; // 예시

    public RagApiServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String requestDietGeneration(RagDietContext context) {
        try {
            String json = objectMapper.writeValueAsString(context);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            // FastAPI 쪽은 POST로 받는 게 일반적
            String response = restTemplate.postForObject(ragUrl, entity, String.class);

            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize RagDietContext", e);
        }
    }
}
