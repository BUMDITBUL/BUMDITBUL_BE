package org.example.bumditbul_be.domain.auth.presentation.dto.request.PasswordResetRequest;
import jakarta.validation.constraints.Email;import jakarta.validation.constraints.NotBlank;import jakarta.validation.constraints.Pattern;
public record PasswordResetRequest(@Email @NotBlank String email, @Pattern(regexp = "^(?=.*[^a-zA-Z0-9]).{8,}$") String newPassword) {}
