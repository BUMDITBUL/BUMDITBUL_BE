package org.example.bumditbul_be.domain.auth.presentation.dto.request.GoogleOauthRequest;
import jakarta.validation.constraints.NotBlank;
public record GoogleOauthRequest(@NotBlank String idToken) {}
