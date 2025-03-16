package dev.patriciafb.stackmentor.services;

import dev.patriciafb.stackmentor.models.User;
import dev.patriciafb.stackmentor.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        User newUser = new User("test", "test@example.com", "password123");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User registeredUser = userService.registerUser(newUser);
        assertNotNull(registeredUser);
        assertEquals("test@example.com", registeredUser.getEmail());
        assertEquals("test", registeredUser.getUsername());
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        User existingUser = new User("test", "test@example.com", "password123");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(new User("test", "test@example.com", "password123"));
        });
        assertEquals("El Usuario ya está registrado", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void testLoginUser_Success() {
        User mockUser = new User("test", "test@example.com", "password123");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        Optional<User> loggedInUser = userService.logginUser("test@example.com", "password123");
        assertTrue(loggedInUser.isPresent());
        assertEquals("test@example.com", loggedInUser.get().getEmail());
        assertEquals("password123", loggedInUser.get().getPassword());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testLoginUser_Failure_WrongPassword() {
        User mockUser = new User("test", "test@example.com", "password123");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        Optional<User> loggedInUser = userService.logginUser("test@example.com", "wrongPassword");
        assertFalse(loggedInUser.isPresent());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testLoginUser_Failure_UserNotFound() {

        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());
        Optional<User> loggedInUser = userService.logginUser("unknown@example.com", "password123");
        assertFalse(loggedInUser.isPresent());
        verify(userRepository, times(1)).findByEmail("unknown@example.com");
    }

    @Test
    void testGetUserByEmail_Success() {
        User mockUser = new User("test", "test@example.com", "password123");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        Optional<User> user = userService.getUserByEmail("test@example.com");
        assertTrue(user.isPresent());
        assertEquals("test@example.com", user.get().getEmail());
        assertEquals("test", user.get().getUsername());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testGetUserByEmail_UserNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());
        Optional<User> user = userService.getUserByEmail("unknown@example.com");
        assertFalse(user.isPresent());
        verify(userRepository, times(1)).findByEmail("unknown@example.com");
    }
}