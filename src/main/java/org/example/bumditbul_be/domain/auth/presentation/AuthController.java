package org.example.bumditbul_be.domain.auth.presentation.AuthController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.bumditbul_be.domain.auth.presentation.dto.request.*;
import org.example.bumditbul_be.domain.auth.presentation.dto.response.*;
import org.example.bumditbul_be.auth.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "인증 API")
public class AuthController {
    private final EmailAuthService emailAuthService;
    private final LocalAuthService localAuthService;
    private final TokenAuthService tokenAuthService;
    private final OAuthService oAuthService;
    private final AccountService accountService;

    public AuthController(EmailAuthService emailAuthService, LocalAuthService localAuthService, TokenAuthService tokenAuthService, OAuthService oAuthService, AccountService accountService) {
        this.emailAuthService = emailAuthService;
        this.localAuthService = localAuthService;
        this.tokenAuthService = tokenAuthService;
        this.oAuthService = oAuthService;
        this.accountService = accountService;
    }

    @Operation(summary = "이메일 인증코드 발송")
    @PostMapping("/email/send") public EmailSendResponse send(@Valid @RequestBody EmailSendRequest req){ return emailAuthService.sendEmail(req);}    
    @Operation(summary = "이메일 인증코드 검증")
    @PostMapping("/email/verify") public EmailVerifyResponse verify(@Valid @RequestBody EmailVerifyRequest req){ return emailAuthService.verifyEmail(req);}    
    @Operation(summary = "회원가입")
    @PostMapping("/signup") public TokenResponse signup(@Valid @RequestBody SignupRequest req){ return localAuthService.signup(req);}    
    @Operation(summary = "로그인")
    @PostMapping("/login") public TokenResponse login(@Valid @RequestBody LoginRequest req){ return localAuthService.login(req);}    
    @Operation(summary = "액세스 토큰 재발급")
    @PostMapping("/token/refresh") public AccessTokenResponse refresh(@Valid @RequestBody RefreshRequest req){ return tokenAuthService.refresh(req);}    
    @Operation(summary = "로그아웃")
    @PostMapping("/logout") public void logout(@Valid @RequestBody LogoutRequest req){ tokenAuthService.logout(req);}    
    @Operation(summary = "비밀번호 재설정")
    @PatchMapping("/password/reset") public void reset(@Valid @RequestBody PasswordResetRequest req){ localAuthService.resetPassword(req);}    
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/withdraw") public void withdraw(@RequestHeader("X-USER-ID") String userId){ accountService.withdraw(userId); }
    @Operation(summary = "Google OAuth 로그인")
    @PostMapping("/oauth/google") public TokenResponse google(@Valid @RequestBody GoogleOauthRequest req){ return oAuthService.googleOauth(req); }
}
