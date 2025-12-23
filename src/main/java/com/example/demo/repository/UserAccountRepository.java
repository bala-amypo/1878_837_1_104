package com.example.demo.repository;

import com.example.demo.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    // ✅ Now VALID because UserAccount has "username"
    Optional<UserAccount> findByUsername(String username);
}
