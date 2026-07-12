package com.fabio.gamememories.repository;

import com.fabio.gamememories.entity.LifeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeEventRepository extends JpaRepository<LifeEvent, Long> {
}
