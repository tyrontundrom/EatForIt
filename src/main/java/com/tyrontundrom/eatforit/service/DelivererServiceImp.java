package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
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
class DelivererServiceImp implements DelivererService {

    private final DelivererJpaRepository delivererJpaRepository;
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public List<DelivererDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, DelivererDto delivererDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<DelivererDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
