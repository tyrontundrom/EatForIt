package com.tyrontundrom.eatforit.service;

import com.google.common.base.Objects;
import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.OrderDto;
import com.tyrontundrom.eatforit.model.Deliverer;
import com.tyrontundrom.eatforit.model.Order;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tyrontundrom.eatforit.utils.ConverterUtils.convert;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "deliverers")
public class DelivererServiceImp implements DelivererService {

    private final DelivererJpaRepository delivererJpaRepository;
    private final OrderJpaRepository orderJpaRepository;

    @Override
    @Cacheable(cacheNames = "deliverers")
    public List<DelivererDto> getAll() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return delivererJpaRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(cacheNames = "deliverers", allEntries = true)
    public void put(UUID uuid, DelivererDto delivererDto) {
        if (!Objects.equal(delivererDto.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // TODO: check newDeliverer method
        Deliverer deliverer = delivererJpaRepository.findByUuid(delivererDto.getUuid())
                .orElseGet(() -> newDeliverer(uuid).get());

        List<Order> orders = new ArrayList<>();
        if (delivererDto.getOrders() != null) {
            for (OrderDto o : delivererDto.getOrders()) {
                Order order = orderJpaRepository.findByUuid(o.getUuid())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                orders.add(order);
            }
        }
        deliverer.setPersonalData(convert(delivererDto.getPersonalDataDto()));
        deliverer.setLogginData(convert(delivererDto.getLogginDataDto()));
        deliverer.setArchive(delivererDto.getArchive());
        deliverer.setOrders(orders);

        if (deliverer.getId() == null) {
            delivererJpaRepository.save(deliverer);
        }
    }

    @Override
    public void delete(UUID uuid) {
        Deliverer deliverer = delivererJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        delivererJpaRepository.delete(deliverer);
    }

    @Override
    public Optional<DelivererDto> getByUuid(UUID uuid) {
        return delivererJpaRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    private Optional<Deliverer> newDeliverer(UUID uuid) {
        return delivererJpaRepository.findByUuid(uuid);
    }
}
