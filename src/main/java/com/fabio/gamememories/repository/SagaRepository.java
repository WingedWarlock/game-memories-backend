package com.fabio.gamememories.repository;

import com.fabio.gamememories.entity.Saga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaRepository extends JpaRepository<Saga, Long> {
}
