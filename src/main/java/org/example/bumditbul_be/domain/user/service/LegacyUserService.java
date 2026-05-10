package org.example.bumditbul_be.domain.user.service.LegacyUserService;

public interface UserService {
    UserProfile getMe();
    UserProfile updateMe(String nickname, String school);
    String updateProfileImage();
    String getExamDate();
    void setExamDate(String examDate);
}
