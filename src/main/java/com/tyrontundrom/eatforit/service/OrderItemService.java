package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.OrderItemDto;
import com.tyrontundrom.eatforit.model.DiscountCode;
import com.tyrontundrom.eatforit.model.OrderItem;
import com.tyrontundrom.eatforit.model.enums.PriceType;
import jakarta.activation.UnsupportedDataTypeException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemService {
    List<OrderItem> getAll();
    void put(UUID uuid, OrderItem orderItem);
    void delete(UUID uuid);
    Optional<OrderItem> getByUuid(UUID uuid);
    BigDecimal calculatePrice(List<OrderItem> orderItems, BigDecimal startPrice, PriceType priceType) throws UnsupportedDataTypeException;
    BigDecimal applyDiscount(DiscountCode discountCode, BigDecimal orderGrossPrice)throws UnsupportedDataTypeException;

}
