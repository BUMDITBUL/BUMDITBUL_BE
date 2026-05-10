package org.example.bumditbul_be.domain.auth.domain.repository.UserRepository;

import org.example.bumditbul_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
