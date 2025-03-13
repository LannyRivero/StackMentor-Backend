package dev.patriciafb.stackmentor.services;

import dev.patriciafb.stackmentor.models.User;
import dev.patriciafb.stackmentor.repositories.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        userRepository.findByEmail(user.getEmail())
            .ifPresent(existing -> {
                throw new RuntimeException("El Usuario ya está registrado");
            });
        return userRepository.save(user);
    }
    

    public Optional<User> logginUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));

    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}