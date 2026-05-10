package org.example.bumditbul_be.domain.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.bumditbul_be.domain.BaseTimeEntity;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {
    @Id @UuidGenerator
    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    @Column(length = 255)
    private String password;
    @Column(nullable = false, unique = true, length = 10)
    private String nickname;

    @Builder
    public UserEntity(String email, String password, String nickname) {this.email=email; this.password=password; this.nickname=nickname;}
}
