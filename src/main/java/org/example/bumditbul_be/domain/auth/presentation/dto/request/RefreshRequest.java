package org.example.bumditbul_be.domain.auth.presentation.dto.request.RefreshRequest;
import jakarta.validation.constraints.NotBlank;
public record RefreshRequest(@NotBlank String refreshToken) {}
