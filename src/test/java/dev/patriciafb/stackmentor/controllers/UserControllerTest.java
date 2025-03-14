package dev.patriciafb.stackmentor.controllers;

import dev.patriciafb.stackmentor.models.User;
import dev.patriciafb.stackmentor.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testRegisterUser_Success() {
        User mockUser = new User("test", "test@example.com", "password123");
        when(userService.registerUser(any(User.class))).thenReturn(mockUser);
        ResponseEntity<?> response = userController.registerUser(mockUser);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test", ((User) response.getBody()).getUsername());
    }
    @Test
    void testRegisterUser_Failure() {
        User mockUser = new User("test", "test@example.com", "password123");
        when(userService.registerUser(any(User.class))).thenReturn(null);
        ResponseEntity<?> response = userController.registerUser(mockUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No se pudo registrar el usuario.", response.getBody());
    }
    @Test
    void testLoginUser_Success() {
        User mockUser = new User("test", "test@example.com", "password123");
        when(userService.logginUser("test@example.com", "password123"))
                .thenReturn(Optional.of(mockUser));
        ResponseEntity<?> response = userController.loginUser(mockUser);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test@example.com", ((User) response.getBody()).getEmail());
    }
    @Test
    void testLoginUser_Failure() {
        User mockUser = new User("test", "test@example.com", "password123");
        when(userService.logginUser("test@example.com", "wrongPassword"))
                .thenReturn(Optional.empty());
        ResponseEntity<?> response = userController.loginUser(mockUser);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales incorrectas", response.getBody());
    }
    @Test
    void testGetCurrentUser_Success() {
        User mockUser = new User("test", "test@example.com", "password123");
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        ResponseEntity<?> response = userController.getCurrentUser("test@example.com");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test@example.com", ((User) response.getBody()).getEmail());
    }
    @Test
    void testGetCurrentUser_Failure() {
        when(userService.getUserByEmail("unknown@example.com")).thenReturn(Optional.empty());
        ResponseEntity<?> response = userController.getCurrentUser("unknown@example.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado", response.getBody());
    }
}
