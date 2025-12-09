package com.nutricare.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nutricare.config.security.CustomUserDetails;
import com.nutricare.model.dto.DietResult;
import com.nutricare.model.dto.RagDietContext;
import com.nutricare.model.service.DietResultService;
import com.nutricare.model.service.RagApiService;
import com.nutricare.model.service.RagDietContextService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/diet-recommendations") // [통합] 식단 추천 관련 모든 기능의 진입점
@Tag(name = "DietRecommendation API", description = "AI 맞춤형 식단 생성, 조회 및 관리 API")
public class DietRecommendationController {

    private final RagDietContextService ragDietContextService;
    private final RagApiService ragApiService;
    private final DietResultService dietResultService;

    public DietRecommendationController(RagDietContextService ragDietContextService,
                                        RagApiService ragApiService,
                                        DietResultService dietResultService) {
        this.ragDietContextService = ragDietContextService;
        this.ragApiService = ragApiService;
        this.dietResultService = dietResultService;
    }

    // ===========================
    // 1. AI 식단 생성 (Action)
    // ===========================
    @Operation(summary = "AI 식단 추천 생성", description = "특정 추천 기록(recId)을 기반으로 AI에게 식단을 요청하고 결과를 저장합니다.")
    @PostMapping("/{recId}")
    public ResponseEntity<?> generateDietResult(
            @Parameter(description = "식단 추천 기록 ID", required = true)
            @PathVariable Long recId) {

        // 1. DB에서 컨텍스트 조회
        RagDietContext context = ragDietContextService.getContextForRec(recId);

        // 2. AI(FastAPI) 요청
        String ragResponseJson = ragApiService.requestDietGeneration(context);

        // 3. 결과 파싱 및 DB 저장
        dietResultService.saveDietResultsFromJson(recId, ragResponseJson);

        return ResponseEntity.ok(ragResponseJson);
    }

    // ===========================
    // 2. 생성된 식단 목록 조회 (List)
    // ===========================
    @Operation(summary = "추천 식단 목록 조회", description = "특정 추천 기록(recId)에 포함된 모든 메뉴 리스트를 조회합니다.")
    @GetMapping("/{recId}")
    public ResponseEntity<?> getListByRecId(
            @Parameter(description = "식단 추천 기록 ID", required = true)
            @PathVariable("recId") Long recId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            checkAuthority(recId, userDetails); // 권한 검사

            List<DietResult> list = dietResultService.getDietResultsByRecId(recId);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===========================
    // 3. 식단 상세 조회 (Detail)
    // ===========================
    @Operation(summary = "식단 상세 조회", description = "생성된 특정 메뉴(resultId)의 상세 정보를 조회합니다.")
    @GetMapping("/results/{resultId}") // URL: /api/diet-recommendations/results/{resultId}
    public ResponseEntity<?> getDetail(
            @Parameter(description = "개별 식단 결과 ID (diet_result PK)", required = true)
            @PathVariable("resultId") Long resultId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            DietResult result = dietResultService.getDietResultById(resultId);
            if (result != null) {
                checkAuthority(result.getRecId(), userDetails); // 주인 확인
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===========================
    // 4. 식단 삭제 (Delete)
    // ===========================
    @Operation(summary = "식단 삭제", description = "생성된 특정 메뉴(resultId)를 삭제합니다.")
    @DeleteMapping("/results/{resultId}") // URL: /api/diet-recommendations/results/{resultId}
    public ResponseEntity<?> delete(
            @PathVariable("resultId") Long resultId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            DietResult origin = dietResultService.getDietResultById(resultId);
            
            if (origin == null) {
                return new ResponseEntity<>("식단 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            }
            
            checkAuthority(origin.getRecId(), userDetails); // 주인 확인
            
            boolean isDeleted = dietResultService.deleteDietResult(resultId);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ===========================
    // Helper Method (권한 검사)
    // ===========================
    private void checkAuthority(Long recId, CustomUserDetails userDetails) {
        // 1. 해당 추천 기록의 주인 확인
        RagDietContext context = ragDietContextService.getContextForRec(recId);
        
        // 2. 관리자는 통과
        if (userDetails.getUser().getRole().equals("ADMIN")) {
            return;
        }

        // 3. 본인 확인
        if (!context.getUserId().equals(userDetails.getUser().getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 식단에 대한 접근 권한이 없습니다.");
        }
    }
}