package com.fabio.gamememories.repository;

import com.fabio.gamememories.entity.GameMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameMemoryRepository extends JpaRepository<GameMemory, Long> {
    List<GameMemory> findByGameId(Long gameId);
}
