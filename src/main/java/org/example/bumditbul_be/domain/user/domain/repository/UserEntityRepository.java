package org.example.bumditbul_be.domain.user.domain.repository.UserEntityRepository;

import org.example.bumditbul_be.domain.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
