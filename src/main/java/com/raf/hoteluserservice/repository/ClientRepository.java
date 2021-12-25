package com.raf.hoteluserservice.repository;

import com.raf.hoteluserservice.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByUsernameAndPassword(String username, String password);
    Optional<Client> findClientByEmailAndPassword(String email, String password);
    Optional<Client> findClientByEmail(String email);
}
