package com.mywallet.api.service;

import com.mywallet.api.configuration.SecurityConfig;
import com.mywallet.api.dto.user.UserLoginDto;
import com.mywallet.api.dto.user.UserRegisterDto;
import com.mywallet.api.entity.User;
import com.mywallet.api.enums.UserEnum;
import com.mywallet.api.exception.user.UserEmailPasswordIncorrect;
import com.mywallet.api.exception.user.UserNotFoundForEmail;
import com.mywallet.api.interfaces.UserMapper;
import com.mywallet.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    SecurityConfig securityConfig;
    UserRepository userRepository;
    UserMapper userMapper;
    EmailService emailService;

    public UserService(SecurityConfig securityConfig, UserRepository userRepository, UserMapper userMapper, EmailService emailService) {
        this.securityConfig = securityConfig;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }

    public void register(UserRegisterDto userRegisterDto) {
        User user = userMapper.fromRegisterDtotoUser(userRegisterDto);
        user.setPassword(securityConfig.passwordEncoder().encode(userRegisterDto.password()));
        user.setEmailVerified(UserEnum.EMAIL_NOT_VERIFIED.value);
        userRepository.save(user);
        this.sendEmailVerification(user);
    }

    private void sendEmailVerification(User user) {
        String verificationLink = "http://localhost:8080/api/v1/user/verify-email/" + user.getId();
        String emailSubject = "Verifique seu email - MyWallet";
        String emailBody = "Olá " + user.getUsername() + ",\n\n" +
            "Para ativar sua conta, clique no link abaixo:\n" +
            verificationLink + "\n\n" +
            "Obrigado!";

        emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
    }

    public void login (UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.email()).orElseThrow(() -> new UserNotFoundForEmail(userLoginDto.email()));

        if (!securityConfig.passwordEncoder().matches(userLoginDto.password(), user.getPassword())) {
            throw new UserEmailPasswordIncorrect();
        }
    }

    public void verifyEmail(java.util.UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundForEmail("User not found"));
        user.setEmailVerified(UserEnum.EMAIL_VERIFIED.value);
        userRepository.save(user);
    }
}
