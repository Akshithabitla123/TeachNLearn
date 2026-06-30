package com.example.Edumate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Edumate.model.User;

public interface UserRepo extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<User> findByEmail(String email);
    List<User> findTop3ByOrderByRatingDesc();
}
