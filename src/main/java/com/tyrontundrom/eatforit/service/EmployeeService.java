package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.EmployeeDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {
    List<EmployeeDto> getAll();
    void put(UUID uuid, EmployeeDto employeeDto);
    void delete(UUID uuid);
    Optional<EmployeeDto> getByUuid(UUID uuid);
}
