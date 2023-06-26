package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserDto> getAll();
    void put(UUID uuid, UserDto userDto);
    void delete(UUID uuid);
    Optional<UserDto> getByUuid(UUID uuid);
    void validateNewOperation(UUID uuid, UserDto userDto);
}
