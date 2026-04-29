package com.mywallet.api.service;

import com.mywallet.api.configuration.JwtTokenProvider;
import com.mywallet.api.configuration.SecurityConfig;
import com.mywallet.api.dto.user.UserLoginDto;
import com.mywallet.api.dto.user.UserRegisterDto;
import com.mywallet.api.entity.User;
import com.mywallet.api.enums.UserEnum;
import com.mywallet.api.exception.user.UserEmailPasswordIncorrect;
import com.mywallet.api.exception.user.UserNotFoundForEmail;
import com.mywallet.api.interfaces.UserMapper;
import com.mywallet.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${APP_URL}")
    String appUrl;
    SecurityConfig securityConfig;
    UserRepository userRepository;
    UserMapper userMapper;
    EmailService emailService;
    JwtTokenProvider jwtTokenProvider;
    WalletService walletService;

    public AuthService(SecurityConfig securityConfig,
                       UserRepository userRepository,
                       UserMapper userMapper,
                       EmailService emailService,
                       JwtTokenProvider jwtTokenProvider,
                       WalletService walletService) {
        this.securityConfig = securityConfig;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.walletService = walletService;
    }

    @Transactional
    public void register(UserRegisterDto userRegisterDto) {
        User user = userMapper.fromRegisterDtotoUser(userRegisterDto);
        user.setPassword(securityConfig.passwordEncoder().encode(userRegisterDto.password()));
        user.setEmailVerified(UserEnum.EMAIL_NOT_VERIFIED);
        userRepository.save(user);
        walletService.createWallet(user);
        this.sendEmailVerification(user);
    }

    private void sendEmailVerification(User user) {
        String verificationLink = this.appUrl + "/api/v1/user/verify-email/" + user.getId();
        String emailSubject = "Verifique seu email - MyWallet";
        String emailBody = "Olá " + user.getUsername() + ",\n\n" +
            "Para ativar sua conta, clique no link abaixo:\n" +
            verificationLink + "\n\n" +
            "Obrigado!";

        emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
    }

    public String login(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.email()).orElseThrow(() -> new UserNotFoundForEmail(userLoginDto.email()));

        if (!securityConfig.passwordEncoder().matches(userLoginDto.password(), user.getPassword())) {
            throw new UserEmailPasswordIncorrect();
        }

        return jwtTokenProvider.generateToken(user.getEmail());
    }
}
