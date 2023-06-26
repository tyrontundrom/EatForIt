package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.EmployeeDto;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.EmployeeJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class EmployeeServiceImp implements EmployeeService {

    private final EmployeeJpaRepository employeeJpaRepository;

    @Override
    public List<EmployeeDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, EmployeeDto employeeDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<EmployeeDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
