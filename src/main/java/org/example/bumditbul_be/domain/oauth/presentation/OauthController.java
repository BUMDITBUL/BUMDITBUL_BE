package org.example.bumditbul_be.domain.oauth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/domain/oauth")
@Tag(name = "OAuth-Domain", description = "OAuth 도메인 스켈레톤 API")
public class OauthController {
    @Operation(summary = "도메인 OAuth 컨트롤러 상태 확인")
    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "ok"); }
}
