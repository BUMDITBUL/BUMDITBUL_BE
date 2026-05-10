package org.example.bumditbul_be.domain.auth.service.EmailAuthServiceImpl;

import org.example.bumditbul_be.auth.EmailCodeService;
import org.example.bumditbul_be.auth.UserRepository;
import org.example.bumditbul_be.domain.auth.presentation.dto.request.*;
import org.example.bumditbul_be.domain.auth.presentation.dto.response.*;
import org.springframework.stereotype.Service;

@Service
public class EmailAuthServiceImpl implements EmailAuthService {
    private final EmailCodeService emailCodeService;
    private final UserRepository userRepository;

    public EmailAuthServiceImpl(EmailCodeService emailCodeService, UserRepository userRepository) {this.emailCodeService = emailCodeService; this.userRepository = userRepository;}
    @Override public EmailSendResponse sendEmail(EmailSendRequest request) { emailCodeService.issue(request.email()); return new EmailSendResponse("인증번호가 전송되었습니다.", userRepository.existsByEmail(request.email())); }
    @Override public EmailVerifyResponse verifyEmail(EmailVerifyRequest request) { boolean ok = emailCodeService.verify(request.email(), request.code()); if (ok) emailCodeService.markVerified(request.email()); return new EmailVerifyResponse(ok); }
    @Override public void validateVerifiedEmail(String email) { if (!emailCodeService.isVerified(email)) throw new RuntimeException("EMAIL_NOT_VERIFIED"); }
}
