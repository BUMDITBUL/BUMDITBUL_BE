package org.example.bumditbul_be.domain.auth.service.TokenAuthServiceImpl;

import org.example.bumditbul_be.auth.JwtProvider;
import org.example.bumditbul_be.auth.RefreshTokenStore;
import org.example.bumditbul_be.domain.auth.presentation.dto.request.*;
import org.example.bumditbul_be.domain.auth.presentation.dto.response.*;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthServiceImpl implements TokenAuthService {
    private final JwtProvider jwtProvider;
    private final RefreshTokenStore refreshTokenStore;

    public TokenAuthServiceImpl(JwtProvider jwtProvider, RefreshTokenStore refreshTokenStore) { this.jwtProvider = jwtProvider; this.refreshTokenStore = refreshTokenStore; }
    @Override public AccessTokenResponse refresh(RefreshRequest request) {
        String userId = jwtProvider.parse(request.refreshToken()).getSubject();
        String stored = refreshTokenStore.get(userId).orElseThrow(() -> new RuntimeException("INVALID_REFRESH_TOKEN"));
        if (!stored.equals(request.refreshToken())) throw new RuntimeException("INVALID_REFRESH_TOKEN");
        String rotated = jwtProvider.createRefreshToken(userId);
        refreshTokenStore.save(userId, rotated);
        return new AccessTokenResponse(jwtProvider.createAccessToken(userId));
    }
    @Override public void logout(LogoutRequest request) { refreshTokenStore.delete(jwtProvider.parse(request.refreshToken()).getSubject()); }
    @Override public TokenResponse issueTokens(String userId) {
        String access = jwtProvider.createAccessToken(userId);
        String refresh = jwtProvider.createRefreshToken(userId);
        refreshTokenStore.save(userId, refresh);
        return new TokenResponse(access, refresh);
    }
}
