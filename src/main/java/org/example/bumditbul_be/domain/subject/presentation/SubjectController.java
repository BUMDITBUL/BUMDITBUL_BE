package org.example.bumditbul_be.domain.subject.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/domain/subjects")
@Tag(name = "Subject-Domain", description = "과목 도메인 스켈레톤 API")
public class SubjectController {
    @Operation(summary = "도메인 과목 컨트롤러 상태 확인")
    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "ok"); }
}
