package com.raf.hoteluserservice.repository;

import com.raf.hoteluserservice.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findManagerByUsernameAndPassword(String username, String password);
    Optional<Manager> findManagerByEmailAndPassword(String email, String password);
    Optional<Manager> findManagerByEmail(String email);
}
