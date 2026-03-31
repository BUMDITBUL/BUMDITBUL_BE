package org.example.bumditbul_be.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {

    GOOGLE("GOOGLE"),
    APPLE("APPLE");

    private final String value;

    public static OAuthProvider from(String value) {
        for (OAuthProvider p : values()) {
            if (p.value.equalsIgnoreCase(value)) return p;
        }
        throw new IllegalArgumentException("Unknown provider: " + value);
    }
}
