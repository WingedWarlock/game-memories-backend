package com.fabio.gamememories.repository;

import com.fabio.gamememories.entity.SavePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavePointRepository extends JpaRepository<SavePoint, Long> {
    List<SavePoint> findByRunId(Long runId);
}
