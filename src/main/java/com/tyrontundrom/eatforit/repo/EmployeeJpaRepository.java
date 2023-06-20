package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUuid(UUID uuid);
}
