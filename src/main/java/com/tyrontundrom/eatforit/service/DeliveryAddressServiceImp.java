package com.tyrontundrom.eatforit.service;

import com.google.common.base.Objects;
import com.tyrontundrom.eatforit.dto.DeliveryAddressDto;
import com.tyrontundrom.eatforit.model.DeliveryAddress;
import com.tyrontundrom.eatforit.model.User;
import com.tyrontundrom.eatforit.repo.DeliveryAddressJpaRepository;
import com.tyrontundrom.eatforit.repo.UserJpaRepository;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeliveryAddressServiceImp implements DeliveryAddressService {

    private final DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public List<DeliveryAddressDto> getAll() {
        return deliveryAddressJpaRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, DeliveryAddressDto deliveryAddressDto) {
        if (!Objects.equal(deliveryAddressDto.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userJpaRepository.findByUuid(deliveryAddressDto.getUserDto().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        DeliveryAddress deliveryAddress = deliveryAddressJpaRepository.findByUuid(deliveryAddressDto.getUuid())
                .orElseGet(() -> newDeliveryAddress(uuid, user));

        if (!Objects.equal(deliveryAddress.getUser().getUuid(), deliveryAddressDto.getUserDto().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        deliveryAddress.setDescription(deliveryAddressDto.getDescription());
        deliveryAddress.setStreet(deliveryAddressDto.getStreet());
        deliveryAddress.setStreetNumber(deliveryAddressDto.getStreetNumber());
        deliveryAddress.setLocalNumber(deliveryAddressDto.getLocalNumber());
        deliveryAddress.setPostalCode(deliveryAddressDto.getPostalCode());
        deliveryAddress.setCity(deliveryAddressDto.getCity());
        deliveryAddress.setBorough(deliveryAddressDto.getBorough());
        deliveryAddress.setCountry(deliveryAddressDto.getCountry());
        deliveryAddress.setState(deliveryAddressDto.getState());

        if (deliveryAddress.getId() == null) {
            deliveryAddressJpaRepository.save(deliveryAddress);
        }
    }

    @Override
    public void delete(UUID uuid) {
        DeliveryAddress deliveryAddress = deliveryAddressJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        deliveryAddressJpaRepository.delete(deliveryAddress);
    }

    @Override
    public Optional<DeliveryAddressDto> getByUuid(UUID uuid) {
        return deliveryAddressJpaRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private DeliveryAddress newDeliveryAddress(UUID uuid, User user) {
        return new DeliveryAddress().builder()
                .uuid(uuid)
                .user(user)
                .build();
    }
}
