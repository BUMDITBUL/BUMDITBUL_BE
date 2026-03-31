package org.example.bumditbul_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"subjects", "oauthAccounts"})
public class User {

    @Id
    @UuidGenerator
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = true, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 10)
    private String nickname;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OauthAccount> oauthAccounts = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 일반 회원가입
    @Builder(builderMethodName = "ofLocal", buildMethodName = "build")
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    // 소셜 회원가입
    @Builder(builderMethodName = "ofSocial", buildMethodName = "build")
    public User(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public boolean isLocalUser() {
        return this.password != null;
    }

    public boolean isSocialUser() {
        return this.password == null;
    }
}
