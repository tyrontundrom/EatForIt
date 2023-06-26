package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.UserDto;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import com.tyrontundrom.eatforit.repo.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class UserServiceImp implements UserService {

    private final UserJpaRepository userJpaRepository;

    @Override
    public List<UserDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, UserDto userDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<UserDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void validateNewOperation(UUID uuid, UserDto userDto) {

    }
}
