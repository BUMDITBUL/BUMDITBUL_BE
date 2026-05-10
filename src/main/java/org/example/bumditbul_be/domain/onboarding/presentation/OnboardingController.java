package org.example.bumditbul_be.domain.onboarding.presentation.OnboardingController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.bumditbul_be.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/onboarding")
@Tag(name = "Onboarding", description = "온보딩 API")
public class OnboardingController {
    private final UserService userService;
    public OnboardingController(UserService userService){ this.userService = userService; }

    @Operation(summary = "온보딩 상태 조회")
    @GetMapping("/status") public Map<String,Object> status(){ return Map.of("completed", false, "step", 1); }
    @Operation(summary = "온보딩 프로필 저장")
    @PatchMapping("/profile") public void profile(@RequestBody Map<String,String> req){ userService.updateMe(req.get("nickname"), req.get("school")); }
}
