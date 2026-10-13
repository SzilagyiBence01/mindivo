package mindivo.mapper;

import mindivo.dto.RegisterUserDto;
import mindivo.dto.UpdateUserDto;
import mindivo.dto.UserResponseDto;
import mindivo.entity.User;

public class UserMapper {

    public static User toEntity(RegisterUserDto dto) {
        User user = new User();

        user.setFullName(dto.getFullName());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    public static UserResponseDto toResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFullName(),
                user.getUserName(),
                user.getEmail()
        );
    }

    public static void updateEntity(User user, UpdateUserDto dto) {
        user.setFullName(dto.getFullName());
        user.setUserName(dto.getUserName());
    }
}