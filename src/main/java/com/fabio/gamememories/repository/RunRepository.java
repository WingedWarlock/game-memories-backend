package com.fabio.gamememories.repository;

import com.fabio.gamememories.entity.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunRepository extends JpaRepository<Run, Long> {
    List<Run> findByGameId(Long gameId);
}
