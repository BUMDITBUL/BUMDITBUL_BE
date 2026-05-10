package org.example.bumditbul_be.auth;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Random;

@Service
public class EmailCodeService {
    private final StringRedisTemplate redis;
    private final JavaMailSender mailSender;
    private final Random random = new Random();

    public EmailCodeService(StringRedisTemplate redis, JavaMailSender mailSender) {
        this.redis = redis;
        this.mailSender = mailSender;
    }

    public String issue(String email) {
        String code = String.format("%06d", random.nextInt(1_000_000));
        redis.opsForValue().set(key(email), code, Duration.ofMinutes(5));
        sendVerificationMail(email, code);
        return code;
    }

    public boolean verify(String email, String code) {
        String saved = redis.opsForValue().get(key(email));
        return saved != null && saved.equals(code);
    }

    public void markVerified(String email){ redis.opsForValue().set(verifiedKey(email), "true", Duration.ofMinutes(30)); }
    public boolean isVerified(String email){ return "true".equals(redis.opsForValue().get(verifiedKey(email))); }

    private void sendVerificationMail(String email, String code) {
        try {
            String html = loadTemplate().replace("183749", code);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(email);
            helper.setSubject("[범딧불] 이메일 인증 코드");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("EMAIL_SEND_FAILED", e);
        }
    }

    private String loadTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/email-template1.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    private String key(String email){ return "email-code:"+email; }
    private String verifiedKey(String email){ return "email-verified:"+email; }
}
