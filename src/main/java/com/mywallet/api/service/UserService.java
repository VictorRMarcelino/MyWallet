package com.mywallet.api.service;

import com.mywallet.api.entity.User;
import com.mywallet.api.enums.UserEnum;
import com.mywallet.api.exception.user.UserNotFoundForEmail;
import com.mywallet.api.interfaces.UserMapper;
import com.mywallet.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public UserService(UserRepository userRepository,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void verifyEmail(java.util.UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundForEmail("User not found"));
        user.setEmailVerified(UserEnum.EMAIL_VERIFIED);
        userRepository.save(user);
    }
}
