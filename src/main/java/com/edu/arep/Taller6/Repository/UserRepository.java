package com.edu.arep.Taller6.Repository;

import com.edu.arep.Taller6.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User>findByEmail(String email);
}
