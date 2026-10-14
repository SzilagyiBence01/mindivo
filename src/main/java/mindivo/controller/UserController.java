package mindivo.controller;

import lombok.AllArgsConstructor;
import mindivo.dto.LoginResponseDto;
import mindivo.dto.LoginUserDto;
import mindivo.dto.RegisterUserDto;
import mindivo.dto.UserResponseDto;
import mindivo.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //
    // Register
    @PostMapping("/register")
    public UserResponseDto register(
            @RequestBody RegisterUserDto dto
    ) {
        return userService.register(dto);
    }

    //
    // Login
    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginUserDto dto
    ) {
        return userService.login(dto);
    }

    //
    // Get own account
    @GetMapping("/account")
    public UserResponseDto getAccount(
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));

        return userService.getAccount(userId);
    }

}