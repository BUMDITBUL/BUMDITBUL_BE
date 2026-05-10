package org.example.bumditbul_be.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey key;
    public JwtProvider(@Value("${app.jwt.secret:01234567890123456789012345678901}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String createAccessToken(String userId) { return createToken(userId, 60 * 30); }
    public String createRefreshToken(String userId) { return createToken(userId, 60L * 60 * 24 * 14); }
    private String createToken(String userId, long sec) {
        Instant now = Instant.now();
        return Jwts.builder().subject(userId).issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(sec))).signWith(key).compact();
    }
    public Claims parse(String token) { return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload(); }
}
