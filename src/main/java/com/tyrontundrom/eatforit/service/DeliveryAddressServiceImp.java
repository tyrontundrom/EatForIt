package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DeliveryAddressDto;
import com.tyrontundrom.eatforit.repo.DeliveryAddressJpaRepository;
import com.tyrontundrom.eatforit.repo.UserJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class DeliveryAddressServiceImp implements DeliveryAddressService {

    private final DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public List<DeliveryAddressDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, DeliveryAddressDto deliveryAddressDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<DeliveryAddressDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
