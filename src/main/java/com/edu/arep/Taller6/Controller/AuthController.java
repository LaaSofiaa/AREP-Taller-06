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
    public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        authService.registerU(email, password);
        return ResponseEntity.ok("Registro Exitoso");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<User> user = authService.loginU(email, password);
        if (user.isPresent()) {
            return ResponseEntity.ok("Login Exitoso");
        } else {
            return ResponseEntity.status(401).body("Credenciales NO válidas");
        }
    }
}
