package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.OpenTimeDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OpenTimeService {
    List<OpenTimeDto> getAll();
    void put(UUID uuid, OpenTimeDto openTimeDto);
    void delete(UUID uuid);
    Optional<OpenTimeDto> getByUuid(UUID uuid);
}
