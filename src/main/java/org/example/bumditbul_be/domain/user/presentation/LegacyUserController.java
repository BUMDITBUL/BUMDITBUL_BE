package org.example.bumditbul_be.domain.user.presentation.LegacyUserController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users/me")
@Tag(name = "User", description = "사용자 프로필 API")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @Operation(summary = "내 프로필 조회")
    @GetMapping public UserProfile me(){ return userService.getMe(); }
    @Operation(summary = "내 프로필 수정")
    @PatchMapping public UserProfile patch(@RequestBody Map<String,String> req){ return userService.updateMe(req.get("nickname"), req.get("school")); }
    @Operation(summary = "프로필 이미지 업로드")
    @PostMapping("/profile-image") public Map<String,String> profile(){ return Map.of("profileImageUrl", userService.updateProfileImage()); }
    @Operation(summary = "시험일 조회")
    @GetMapping("/exam-date") public Map<String,String> examDate(){ return Map.of("examDate", userService.getExamDate()); }
}
