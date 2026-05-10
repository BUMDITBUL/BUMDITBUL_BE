package org.example.bumditbul_be.domain.auth.presentation.dto.request.EmailSendRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record EmailSendRequest(@Email @NotBlank String email) {}
