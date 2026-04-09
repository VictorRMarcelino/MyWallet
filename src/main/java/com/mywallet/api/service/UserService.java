package com.mywallet.api.service;

import com.mywallet.api.configuration.SecurityConfig;
import com.mywallet.api.dto.user.UserLoginDto;
import com.mywallet.api.dto.user.UserRegisterDto;
import com.mywallet.api.entity.User;
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

    public UserService(SecurityConfig securityConfig, UserRepository userRepository, UserMapper userMapper) {
        this.securityConfig = securityConfig;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void register(UserRegisterDto userRegisterDto) {
        User user = userMapper.fromRegisterDtotoUser(userRegisterDto);
        user.setPassword(securityConfig.passwordEncoder().encode(userRegisterDto.password()));
        userRepository.save(user);
    }

    public void login (UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.email()).orElseThrow(() -> new UserNotFoundForEmail(userLoginDto.email()));

        if (!securityConfig.passwordEncoder().matches(userLoginDto.password(), user.getPassword())) {
            throw new UserEmailPasswordIncorrect();
        }
    }
}
