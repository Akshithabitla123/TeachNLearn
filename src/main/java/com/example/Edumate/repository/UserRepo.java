package com.example.Edumate.repository;

import com.example.Edumate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<User> findByEmail(String email);
}
