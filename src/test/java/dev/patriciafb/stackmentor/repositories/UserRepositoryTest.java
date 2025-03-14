package dev.patriciafb.stackmentor.repositories;

import dev.patriciafb.stackmentor.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager; // Inyección del EntityManager

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new JpaRepositoryFactory(entityManager).getRepository(UserRepository.class); // Inyección manual
        
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password123");
        entityManager.persist(user); // Guardamos el usuario en la BD
        entityManager.flush(); // Asegura que los cambios se reflejen en la BD antes de las pruebas
    }

    @Test
    void testFindByEmail_Success() {
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<User> foundUser = userRepository.findByEmail("notfound@example.com");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testExistsByEmail_True() {
        assertTrue(userRepository.existsByEmail("test@example.com"));
    }

    @Test
    void testExistsByEmail_False() {
        assertFalse(userRepository.existsByEmail("notfound@example.com"));
    }

    @Test
    void testExistsByUsername_True() {
        assertTrue(userRepository.existsByUsername("testuser"));
    }

    @Test
    void testExistsByUsername_False() {
        assertFalse(userRepository.existsByUsername("unknownuser"));
    }
}
