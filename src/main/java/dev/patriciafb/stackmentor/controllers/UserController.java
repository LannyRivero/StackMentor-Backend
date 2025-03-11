package dev.patriciafb.stackmentor.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.patriciafb.stackmentor.models.User;
import dev.patriciafb.stackmentor.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private UserService userService;

    // Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.status(201).body("Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Inicio de sesión
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User loggedInUser = userService.login(user.getEmail(), user.getPassword());
        if (loggedInUser == null) {
            return ResponseEntity.status(401).body(null); // Credenciales inválidas
        }
        return ResponseEntity.ok(loggedInUser);
    }
}