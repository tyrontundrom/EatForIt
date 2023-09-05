package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.OpenTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OpenTimeJpaRepository extends JpaRepository<OpenTime, Long> {
    Optional<OpenTime> findByUuid(UUID uuid);
}
