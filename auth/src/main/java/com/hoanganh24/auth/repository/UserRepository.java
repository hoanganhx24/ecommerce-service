package com.hoanganh24.auth.repository;

import com.hoanganh24.auth.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
