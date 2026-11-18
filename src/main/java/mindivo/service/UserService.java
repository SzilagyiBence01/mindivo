package mindivo.service;

import mindivo.dto.*;

public interface UserService {

    UserResponseDto register(RegisterUserDto dto);

    LoginResponseDto login(LoginUserDto dto);

    UserResponseDto getAccount(Long userId);

    UserResponseDto updateProfile(Long userId, UpdateUserDto dto);
}