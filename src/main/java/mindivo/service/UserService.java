package mindivo.service;

import mindivo.dto.LoginResponseDto;
import mindivo.dto.LoginUserDto;
import mindivo.dto.RegisterUserDto;
import mindivo.dto.UserResponseDto;

public interface UserService {

    UserResponseDto register(RegisterUserDto dto);

    LoginResponseDto login(LoginUserDto dto);

    UserResponseDto getAccount(Long userId);
}