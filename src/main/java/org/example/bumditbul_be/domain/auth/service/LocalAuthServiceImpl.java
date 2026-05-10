package org.example.bumditbul_be.domain.auth.service.LocalAuthServiceImpl;

import org.example.bumditbul_be.auth.UserRepository;
import org.example.bumditbul_be.domain.auth.presentation.dto.request.*;
import org.example.bumditbul_be.domain.auth.presentation.dto.response.*;
import org.example.bumditbul_be.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {
    private final UserRepository userRepository;
    private final EmailAuthService emailAuthService;
    private final TokenAuthService tokenAuthService;
    private final PasswordEncoder passwordEncoder;

    public LocalAuthServiceImpl(UserRepository userRepository, EmailAuthService emailAuthService, TokenAuthService tokenAuthService, PasswordEncoder passwordEncoder) {this.userRepository = userRepository; this.emailAuthService = emailAuthService; this.tokenAuthService = tokenAuthService; this.passwordEncoder = passwordEncoder;}
    @Override public TokenResponse signup(SignupRequest request) {
        emailAuthService.validateVerifiedEmail(request.email());
        if (userRepository.existsByEmail(request.email())) throw new RuntimeException("DUPLICATE_EMAIL");
        User user = User.ofLocal().email(request.email()).password(passwordEncoder.encode(request.password())).nickname("user"+System.currentTimeMillis()%10000).build();
        userRepository.save(user);
        return tokenAuthService.issueTokens(user.getUserId());
    }
    @Override public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("INVALID_CREDENTIALS"));
        if (user.getPassword() == null || !passwordEncoder.matches(request.password(), user.getPassword())) throw new RuntimeException("INVALID_CREDENTIALS");
        return tokenAuthService.issueTokens(user.getUserId());
    }
    @Override public void resetPassword(PasswordResetRequest request) {
        emailAuthService.validateVerifiedEmail(request.email());
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
        user.updatePassword(passwordEncoder.encode(request.newPassword()));
    }
}
