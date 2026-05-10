package org.example.bumditbul_be.domain.user.service.LegacyUserServiceImpl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private volatile LocalDate examDate = LocalDate.of(2026,5,11);

    public UserServiceImpl(UserRepository userRepository) { this.userRepository = userRepository; }
    @Override public UserProfile getMe() { return userRepository.getMe(); }
    @Override public UserProfile updateMe(String nickname, String school) {
        UserProfile current = userRepository.getMe();
        return userRepository.saveMe(new UserProfile(current.id(), nickname, school, current.profileImageUrl()));
    }
    @Override public String updateProfileImage() { return "https://example.com/profile.jpg"; }
    @Override public String getExamDate() { return examDate.toString(); }
    @Override public void setExamDate(String examDate) { this.examDate = LocalDate.parse(examDate); }
}
