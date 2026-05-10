package org.example.bumditbul_be.domain.auth.presentation.dto.request.LogoutRequest;
import jakarta.validation.constraints.NotBlank;
public record LogoutRequest(@NotBlank String refreshToken) {}
