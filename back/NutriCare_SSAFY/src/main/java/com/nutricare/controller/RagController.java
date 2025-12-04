package com.nutricare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutricare.model.dto.RagDietContext;
import com.nutricare.model.service.DietResultService;
import com.nutricare.model.service.RagApiService;
import com.nutricare.model.service.RagDietContextService;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagDietContextService ragDietContextService;
    private final RagApiService ragApiService;
    private final DietResultService dietResultService;

    // Lombok 안 쓰니까 생성자 직접 작성
    public RagController(RagDietContextService ragDietContextService,
                         RagApiService ragApiService,
                         DietResultService dietResultService) {
        this.ragDietContextService = ragDietContextService;
        this.ragApiService = ragApiService;
        this.dietResultService = dietResultService;
    }

    /**
     * diet_recommendation.rec_id를 받아서
     * 1) RAG 컨텍스트 조회
     * 2) FastAPI(RAG)에 컨텍스트 전달
     * 3) 응답을 diet_result에 저장
     * 4) 클라이언트에게 RAG 응답을 그대로 내려줌
     */
    @PostMapping("/diet/{recId}")
    public ResponseEntity<?> generateDietResult(@PathVariable Long recId) {

        // 1. DB에서 RAG에 넘길 컨텍스트 가져오기 (join 결과)
        RagDietContext context = ragDietContextService.getContextForRec(recId);

        // 2. FastAPI(RAG)에 컨텍스트 전달하고, 응답(JSON 문자열 or DTO) 받기
        String ragResponseJson = ragApiService.requestDietGeneration(context);

        // 3. RAG 응답을 바탕으로 diet_result 테이블에 저장
        dietResultService.saveDietResultsFromJson(recId, ragResponseJson);

        // 4. 클라이언트에게도 응답을 돌려주기
        return ResponseEntity.ok(ragResponseJson);
    }
}
