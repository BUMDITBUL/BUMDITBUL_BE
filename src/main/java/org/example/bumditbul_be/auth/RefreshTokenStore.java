package org.example.bumditbul_be.auth;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class RefreshTokenStore {
    private final StringRedisTemplate redis;
    public RefreshTokenStore(StringRedisTemplate redis) { this.redis = redis; }

    public void save(String userId, String token) { redis.opsForValue().set(key(userId), token, Duration.ofDays(14)); }
    public Optional<String> get(String userId) { return Optional.ofNullable(redis.opsForValue().get(key(userId))); }
    public void delete(String userId) { redis.delete(key(userId)); }
    private String key(String userId) { return "refresh:" + userId; }
}
