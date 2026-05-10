package org.example.bumditbul_be.domain.auth.service.EmailAuthService;

import org.example.bumditbul_be.domain.auth.presentation.dto.request.*;
import org.example.bumditbul_be.domain.auth.presentation.dto.response.*;

public interface EmailAuthService {
    EmailSendResponse sendEmail(EmailSendRequest request);
    EmailVerifyResponse verifyEmail(EmailVerifyRequest request);
    void validateVerifiedEmail(String email);
}
