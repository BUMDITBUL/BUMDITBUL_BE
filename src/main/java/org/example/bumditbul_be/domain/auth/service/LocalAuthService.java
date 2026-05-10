package org.example.bumditbul_be.domain.auth.service.LocalAuthService;

import org.example.bumditbul_be.domain.auth.presentation.dto.request.*;
import org.example.bumditbul_be.domain.auth.presentation.dto.response.*;

public interface LocalAuthService {
    TokenResponse signup(SignupRequest request);
    TokenResponse login(LoginRequest request);
    void resetPassword(PasswordResetRequest request);
}
