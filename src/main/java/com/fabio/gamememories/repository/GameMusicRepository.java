package com.fabio.gamememories.repository;

import com.fabio.gamememories.entity.GameMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameMusicRepository extends JpaRepository<GameMusic, Long> {
    List<GameMusic> findByGameId(Long gameId);
}
