package org.example.bumditbul_be.domain.oauth.domain.repository.OauthAccountEntityRepository;

import org.example.bumditbul_be.domain.oauth.domain.entity.OauthAccountEntity;
import org.example.bumditbul_be.domain.oauth.domain.enum.OAuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthAccountEntityRepository extends JpaRepository<OauthAccountEntity, String> {
    Optional<OauthAccountEntity> findByProviderAndProviderId(OAuthProviderType provider, String providerId);
}
