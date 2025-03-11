package dev.patriciafb.stackmentor.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import dev.patriciafb.stackmentor.model.User;
import dev.patriciafb.stackmentor.repository.UserRepository;

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
