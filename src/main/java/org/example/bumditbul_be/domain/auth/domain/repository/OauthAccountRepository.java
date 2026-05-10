package org.example.bumditbul_be.domain.auth.domain.repository.OauthAccountRepository;

import org.example.bumditbul_be.entity.OAuthProvider;
import org.example.bumditbul_be.entity.OauthAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthAccountRepository extends JpaRepository<OauthAccount, String> {
    Optional<OauthAccount> findByProviderAndProviderId(OAuthProvider provider, String providerId);
}
