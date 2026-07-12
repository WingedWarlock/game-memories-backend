package com.fabio.gamememories.repository;

import com.fabio.gamememories.entity.HistoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryEventRepository extends JpaRepository<HistoryEvent, Long> {
    List<HistoryEvent> findAllByOrderByOccurredAtDesc();
}
