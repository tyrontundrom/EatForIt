package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.Opentime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OpenTimeJpaRepository extends JpaRepository<Opentime, Long> {
    Optional<Opentime> findByUuid(UUID uuid);
}
