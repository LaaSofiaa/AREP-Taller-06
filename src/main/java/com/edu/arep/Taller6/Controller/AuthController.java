package com.edu.arep.Taller6.Controller;

import com.edu.arep.Taller6.Entity.User;
import com.edu.arep.Taller6.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        authService.registerU(user.getEmail(), user.getPassword());
        return ResponseEntity.ok("Registro Exitoso");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        Optional<User> loggedUser = authService.loginU(user.getEmail(), user.getPassword());
        if (loggedUser.isPresent()) {
            return ResponseEntity.ok("Login Exitoso");
        } else {
            return ResponseEntity.status(401).body("Credenciales NO válidas");
        }
    }
}
