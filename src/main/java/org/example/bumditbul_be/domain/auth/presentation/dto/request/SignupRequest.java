package org.example.bumditbul_be.domain.auth.presentation.dto.request.SignupRequest;
import jakarta.validation.constraints.Email;import jakarta.validation.constraints.NotBlank;import jakarta.validation.constraints.Pattern;
public record SignupRequest(@Email @NotBlank String email, @Pattern(regexp = "^(?=.*[^a-zA-Z0-9]).{8,}$") String password) {}
