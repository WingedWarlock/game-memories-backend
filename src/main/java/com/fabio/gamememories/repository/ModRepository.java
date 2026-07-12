package com.fabio.gamememories.repository;

import com.fabio.gamememories.entity.Mod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModRepository extends JpaRepository<Mod, Long> {
    List<Mod> findByGameId(Long gameId);
}
