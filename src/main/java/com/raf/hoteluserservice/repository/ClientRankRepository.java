package com.raf.hoteluserservice.repository;

import com.raf.hoteluserservice.domain.ClientRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRankRepository extends JpaRepository<ClientRank, Long> {

    Optional<ClientRank> findRankByName(String name);
}
