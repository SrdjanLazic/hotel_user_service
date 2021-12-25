package com.raf.hoteluserservice.repository;

import com.raf.hoteluserservice.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByUsernameAndPassword(String username, String password);
    Optional<Admin> findAdminByEmailAndPassword(String email, String password);
    Optional<Admin> findAdminByEmail(String email);
}
