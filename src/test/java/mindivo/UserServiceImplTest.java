package mindivo;

import mindivo.dto.LoginResponseDto;
import mindivo.dto.LoginUserDto;
import mindivo.dto.RegisterUserDto;
import mindivo.entity.User;
import mindivo.repository.UserRepository;
import mindivo.security.JwtService;
import mindivo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUser() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setFullName("Test User");
        dto.setUserName("testuser");
        dto.setEmail("test@test.com");
        dto.setPassword("password");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUserName(dto.getUserName())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");

        User user = new User();
        user.setId(1L);
        user.setFullName("Test User");
        user.setUserName("testuser");
        user.setEmail("test@test.com");
        user.setPassword("hashedPassword");

        when(userRepository.save(any(User.class))).thenReturn(user);

        var result = userService.register(dto);

        assertEquals("testuser", result.getUserName());
        assertEquals("test@test.com", result.getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void cannotRegisterWithExistingEmail() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setEmail("test@test.com");
        dto.setUserName("testuser");

        User existingUser = new User();

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(existingUser));

        assertThrows(
                RuntimeException.class,
                () -> userService.register(dto)
        );
    }

    @Test
    void loginWithCorrectPassword() {
        LoginUserDto dto = new LoginUserDto();
        dto.setEmail("test@example.com");
        dto.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(1L, "test@example.com")).thenReturn("test-token");

        LoginResponseDto result = userService.login(dto);

        assertNotNull(result);
    }

    @Test
    void loginWithWrongPassword() {
        LoginUserDto dto = new LoginUserDto();
        dto.setEmail("test@test.com");
        dto.setPassword("wrongPassword");

        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("hashedPassword");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> userService.login(dto)
        );
    }
}