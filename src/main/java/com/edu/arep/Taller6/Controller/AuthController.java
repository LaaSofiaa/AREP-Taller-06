package com.edu.arep.Taller6.Controller;

import com.edu.arep.Taller6.Entity.User;
import com.edu.arep.Taller6.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String>register(@RequestParam String email, @RequestParam String password){
        authService.registerU(email, password);
        return ResponseEntity.ok("Registro Exitoso");
    }

    @PostMapping("/login")
    public ResponseEntity<String>login(@RequestParam String email,@RequestParam String password){
        Optional<User> user = authService.loginU(email,password);
        if(user.isPresent()){
            return ResponseEntity.ok("Login Exitoso");
        }else{
            return ResponseEntity.status(401).body("Credenciales NO validas");
        }
    }
}
