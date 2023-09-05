package com.tyrontundrom.eatforit.service;

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
    void add(OrderItem orderItem);
    void delete(OrderItem orderItem);
    Optional<OrderItem> getByUuid(UUID uuid);
    BigDecimal calculatePrice(List<OrderItem> orderItems, BigDecimal startPrice, PriceType priceType) throws UnsupportedDataTypeException;
    BigDecimal applyDiscount(DiscountCode discountCode, BigDecimal orderGrossPrice)throws UnsupportedDataTypeException;

}
