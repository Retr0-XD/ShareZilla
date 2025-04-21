package com.jpa.sharezillamain.repository;

import com.jpa.sharezillamain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// repository/AppUserRepository.java
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}