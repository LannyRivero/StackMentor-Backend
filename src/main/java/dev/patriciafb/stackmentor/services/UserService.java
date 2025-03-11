package dev.patriciafb.stackmentor.services;

import dev.patriciafb.stackmentor.models.User;
import dev.patriciafb.stackmentor.repositories.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private UserRepository userRepository;
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está en uso");
        }
        return userRepository.save(user);
    }
    public User login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return null; // Credenciales inválidas
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(password)) { // Comparación básica
            return null; // Contraseña incorrecta
        }
        return user;
    }
}