package org.example.bumditbul_be.domain.studylog.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/domain/studylogs")
@Tag(name = "StudyLog-Domain", description = "학습로그 도메인 스켈레톤 API")
public class StudylogController {
    @Operation(summary = "도메인 학습로그 컨트롤러 상태 확인")
    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "ok"); }
}
