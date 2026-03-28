package com.mywallet.api.service;

import com.mywallet.api.configuration.SecurityConfig;
import com.mywallet.api.dto.UserRegisterDto;
import com.mywallet.api.entity.User;
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
}
