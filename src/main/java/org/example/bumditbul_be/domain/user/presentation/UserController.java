package org.example.bumditbul_be.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/domain/users")
@Tag(name = "User-Domain", description = "사용자 도메인 스켈레톤 API")
public class UserController {
    @Operation(summary = "도메인 사용자 컨트롤러 상태 확인")
    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "ok"); }
}
