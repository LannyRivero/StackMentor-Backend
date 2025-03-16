package dev.patriciafb.stackmentor.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("test", "test@example.com", "password123");
    }

    @Test
    void testDefaultConstructor() {
        User newUser = new User();
        assertNotNull(newUser);
        assertNull(newUser.getId());
        assertNull(newUser.getUsername());
        assertNull(newUser.getEmail());
        assertNull(newUser.getPassword());
    }

    @Test
    void testParameterizedConstructor() {
        assertNotNull(user);
        assertEquals("test", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testGettersAndSetters() {
        user.setId(1L);
        user.setUsername("newUsername");
        user.setEmail("newEmail@example.com");
        user.setPassword("newPassword");
        assertEquals(1L, user.getId());
        assertEquals("newUsername", user.getUsername());
        assertEquals("newEmail@example.com", user.getEmail());
        assertEquals("newPassword", user.getPassword());
    }

}
