package org.example.bumditbul_be.domain.auth.service.AccountServiceImpl;

import org.example.bumditbul_be.auth.RefreshTokenStore;
import org.example.bumditbul_be.auth.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final RefreshTokenStore refreshTokenStore;

    public AccountServiceImpl(UserRepository userRepository, RefreshTokenStore refreshTokenStore) {this.userRepository = userRepository; this.refreshTokenStore = refreshTokenStore;}
    @Override public void withdraw(String userId) { userRepository.deleteById(userId); refreshTokenStore.delete(userId); }
}
