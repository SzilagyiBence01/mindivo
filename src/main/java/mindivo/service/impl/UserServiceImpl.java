package mindivo.service.impl;

import lombok.AllArgsConstructor;
import mindivo.dto.LoginResponseDto;
import mindivo.dto.LoginUserDto;
import mindivo.dto.RegisterUserDto;
import mindivo.dto.UserResponseDto;
import mindivo.entity.User;
import mindivo.exception.DuplicateResourceException;
import mindivo.mapper.UserMapper;
import mindivo.repository.UserRepository;
import mindivo.security.JwtService;
import mindivo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Register
    @Override
    public UserResponseDto register(RegisterUserDto dto) {

        // Check if the email is already in use
        if (userRepository.findByEmail(dto.getEmail() ).isPresent() ) {
            throw new DuplicateResourceException(
                    "Email is already in use."
            );
        }

        // Check if the username is already in use
        if (userRepository.findByUserName(dto.getUserName() ).isPresent() ) {
            throw new DuplicateResourceException("Username is already in use.");
        }

        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode( user.getPassword() ) );

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    // Login
    @Override
    public LoginResponseDto login(LoginUserDto dto) {

        // Check if the username is exist
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password.")
                );

        // Check if the password is correct
        if (!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Invalid email or password.");
        }

        // Generate JWT token
        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail()
        );

        return new LoginResponseDto(
                token,
                "Bearer",
                UserMapper.toResponse(user)
        );
    }

    // Get account
    @Override
    public UserResponseDto getAccount(Long userId) {

        // Check if the id is existed
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found.")
                );

        return UserMapper.toResponse(user);
    }
}