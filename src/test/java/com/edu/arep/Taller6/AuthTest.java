package com.edu.arep.Taller6;

import com.edu.arep.Taller6.Entity.User;
import com.edu.arep.Taller6.Repository.UserRepository;
import com.edu.arep.Taller6.Service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void registerUser() {
        String email = "sofia@gmail.com";
        String password = "password";
        String hashedPassword = "hashedPassword";
        User user = new User(email, hashedPassword);
        when(passwordEncoder.encode(password)).thenReturn(hashedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);
        User registeredUser = authService.registerU(email, password);
        assertNotNull(registeredUser);
        assertEquals(email, registeredUser.getEmail());
        assertEquals(hashedPassword, registeredUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void loginUserSuccess() {
        String email = "sofia@gmail.com";
        String password = "password";
        String hashedPassword = "hashedPassword";
        User user = new User(email, hashedPassword);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);
        Optional<User> loggedInUser = authService.loginU(email, password);
        assertTrue(loggedInUser.isPresent());
        assertEquals(email, loggedInUser.get().getEmail());
        assertEquals(hashedPassword, loggedInUser.get().getPassword());
    }
    @Test
    void loginUserFailure() {
        String email = "sofia@gmail.com";
        String password = "wrongPassword";
        String hashedPassword = "hashedPassword";
        User user = new User(email, hashedPassword);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);
        Optional<User> loggedInUser = authService.loginU(email, password);
        assertFalse(loggedInUser.isPresent());
    }

    @Test
    void registerUserAlreadyExists() {
        String email = "sofia@gmail.com";
        String password = "password";
        String hashedPassword = "hashedPassword";
        User existingUser = new User(email, hashedPassword);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.registerU(email, password);
        });
        String expectedMessage = "El usuario ya existe";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository, never()).save(any(User.class));
    }

}
