package org.example.bumditbul_be.domain.auth.service.TokenAuthService;

import org.example.bumditbul_be.domain.auth.presentation.dto.request.*;
import org.example.bumditbul_be.domain.auth.presentation.dto.response.*;

public interface TokenAuthService {
    AccessTokenResponse refresh(RefreshRequest request);
    void logout(LogoutRequest request);
    TokenResponse issueTokens(String userId);
}
