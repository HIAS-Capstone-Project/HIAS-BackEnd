package com.hias.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    Optional<User> findByUsername(String username);
}
