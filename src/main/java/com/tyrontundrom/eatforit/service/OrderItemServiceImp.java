package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.model.DiscountCode;
import com.tyrontundrom.eatforit.model.OrderItem;
import com.tyrontundrom.eatforit.model.enums.PriceType;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderItemJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import jakarta.activation.UnsupportedDataTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class OrderItemServiceImp implements OrderItemService {

    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public List<OrderItem> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, OrderItem orderItem) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<OrderItem> getByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public BigDecimal calculatePrice(List<OrderItem> orderItems, BigDecimal startPrice, PriceType priceType) throws UnsupportedDataTypeException {
        return null;
    }

    @Override
    public BigDecimal applyDiscount(DiscountCode discountCode, BigDecimal orderGrossPrice) throws UnsupportedDataTypeException {
        return null;
    }
}
