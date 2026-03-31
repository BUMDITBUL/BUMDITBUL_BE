package org.example.bumditbul_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "oauth_accounts",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_oauth_provider_provider_id",
            columnNames = {"provider", "provider_id"}
        )
    },
    indexes = {
        @Index(name = "idx_oauth_accounts_user_id", columnList = "user_id")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "user")
public class OauthAccount {

    @Id
    @UuidGenerator
    @Column(name = "oauth_id", updatable = false, nullable = false)
    private String oauthId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OAuthProvider provider;

    @Column(name = "provider_id", nullable = false, length = 255)
    private String providerId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public OauthAccount(User user, OAuthProvider provider, String providerId) {
        this.user = user;
        this.provider = provider;
        this.providerId = providerId;
    }
}
