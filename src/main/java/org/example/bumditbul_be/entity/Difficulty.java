package org.example.bumditbul_be.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {

    HIGH("상"),
    MEDIUM("중"),
    LOW("하");

    private final String label;

    public static Difficulty from(String label) {
        for (Difficulty d : values()) {
            if (d.label.equals(label)) return d;
        }
        throw new IllegalArgumentException("Unknown difficulty: " + label);
    }
}
