package com.mywallet.api.interfaces;

import com.mywallet.api.dto.UserRegisterDto;
import com.mywallet.api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromRegisterDtotoUser(UserRegisterDto userRegisterDto);
}
