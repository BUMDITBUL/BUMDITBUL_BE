package org.example.bumditbul_be.domain.auth.service.OAuthServiceImpl;

import org.example.bumditbul_be.auth.GoogleOauthClient;
import org.example.bumditbul_be.auth.OauthAccountRepository;
import org.example.bumditbul_be.auth.UserRepository;
import org.example.bumditbul_be.domain.auth.presentation.dto.request.GoogleOauthRequest;
import org.example.bumditbul_be.domain.auth.presentation.dto.response.TokenResponse;
import org.example.bumditbul_be.entity.OAuthProvider;
import org.example.bumditbul_be.entity.OauthAccount;
import org.example.bumditbul_be.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuthServiceImpl implements OAuthService {
    private final GoogleOauthClient googleOauthClient;
    private final OauthAccountRepository oauthAccountRepository;
    private final UserRepository userRepository;
    private final TokenAuthService tokenAuthService;

    public OAuthServiceImpl(GoogleOauthClient googleOauthClient, OauthAccountRepository oauthAccountRepository, UserRepository userRepository, TokenAuthService tokenAuthService) {this.googleOauthClient = googleOauthClient; this.oauthAccountRepository = oauthAccountRepository; this.userRepository = userRepository; this.tokenAuthService = tokenAuthService;}
    @Override public TokenResponse googleOauth(GoogleOauthRequest request) {
        Map<String, Object> tokenInfo = googleOauthClient.verifyIdToken(request.idToken());
        String sub = tokenInfo.get("sub").toString();
        String email = tokenInfo.get("email").toString();
        User user = oauthAccountRepository.findByProviderAndProviderId(OAuthProvider.GOOGLE, sub)
                .map(OauthAccount::getUser)
                .orElseGet(() -> {
                    User nu = userRepository.findByEmail(email).orElseGet(() -> userRepository.save(User.ofSocial().email(email).nickname("g"+System.currentTimeMillis()%10000).build()));
                    oauthAccountRepository.save(OauthAccount.builder().user(nu).provider(OAuthProvider.GOOGLE).providerId(sub).build());
                    return nu;
                });
        return tokenAuthService.issueTokens(user.getUserId());
    }
}
