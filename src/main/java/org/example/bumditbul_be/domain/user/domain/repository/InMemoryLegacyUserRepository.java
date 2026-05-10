package org.example.bumditbul_be.domain.user.domain.repository.InMemoryLegacyUserRepository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, UserProfile> users = new ConcurrentHashMap<>();
    private volatile UserProfile me = new UserProfile(1L, "홍길동", "대전고등학교", "https://example.com/profile.jpg");

    @Override public UserProfile getMe() { return me; }
    @Override public UserProfile saveMe(UserProfile profile) { me = profile; return me; }
    @Override public void save(String email, UserProfile profile) { users.put(email, profile); }
    @Override public boolean existsByEmail(String email) { return users.containsKey(email); }
    @Override public void clear() { users.clear(); }
}
