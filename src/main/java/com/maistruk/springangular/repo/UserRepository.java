package com.maistruk.springangular.repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maistruk.springangular.model.*;

public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByEmail(String email);
}
