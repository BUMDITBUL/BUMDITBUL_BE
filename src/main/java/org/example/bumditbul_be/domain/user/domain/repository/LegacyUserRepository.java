package org.example.bumditbul_be.domain.user.domain.repository.LegacyUserRepository;

public interface UserRepository {
    UserProfile getMe();
    UserProfile saveMe(UserProfile profile);
    void save(String email, UserProfile profile);
    boolean existsByEmail(String email);
    void clear();
}
