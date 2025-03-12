package com.edu.arep.Taller6.Service;

import com.edu.arep.Taller6.Entity.User;
import com.edu.arep.Taller6.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerU(String email, String password){
        String hashps = passwordEncoder.encode(password);
        User user = new User(email, hashps);
        return userRepository.save(user);
    }

    public Optional<User>loginU(String email, String password){
        Optional<User>user = userRepository.findByEmail(email);
        if(user.isPresent()&&passwordEncoder.matches(password,user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}
