package org.example.bumditbul_be.domain.auth.service.OAuthService;

import org.example.bumditbul_be.domain.auth.presentation.dto.request.GoogleOauthRequest;
import org.example.bumditbul_be.domain.auth.presentation.dto.response.TokenResponse;

public interface OAuthService {
    TokenResponse googleOauth(GoogleOauthRequest request);
}
