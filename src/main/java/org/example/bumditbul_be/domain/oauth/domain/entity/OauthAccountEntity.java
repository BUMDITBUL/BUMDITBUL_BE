package org.example.bumditbul_be.domain.oauth.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.bumditbul_be.domain.BaseTimeEntity;
import org.example.bumditbul_be.domain.oauth.domain.enum.OAuthProviderType;
import org.example.bumditbul_be.domain.user.domain.entity.UserEntity;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "oauth_accounts", uniqueConstraints = @UniqueConstraint(name = "uq_oauth_provider_provider_id", columnNames = {"provider", "provider_id"}), indexes = @Index(name = "idx_oauth_accounts_user_id", columnList = "user_id"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthAccountEntity extends BaseTimeEntity {
    @Id @UuidGenerator
    @Column(name = "oauth_id", nullable = false, updatable = false)
    private String oauthId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OAuthProviderType provider;
    @Column(name = "provider_id", nullable = false, length = 255)
    private String providerId;

    @Builder
    public OauthAccountEntity(UserEntity user, OAuthProviderType provider, String providerId) {this.user=user;this.provider=provider;this.providerId=providerId;}
}
