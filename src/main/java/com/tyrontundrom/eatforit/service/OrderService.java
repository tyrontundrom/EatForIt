package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.OrderDto;
import com.tyrontundrom.eatforit.dto.OrderStatusDto;
import com.tyrontundrom.eatforit.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    List<OrderDto> getAll();
    void put(UUID uuid, OrderDto orderDto);
    void delete(UUID uuid);
    Optional<OrderDto> getByUuid(UUID uuid);

    void setIsPaid(OrderDto orderDto);
    void setIsDelivered(OrderDto orderDto, OrderStatusDto orderStatusDto);
    void setIsGivedOut(OrderDto orderDto, OrderStatusDto orderStatusDto);
    UserDto newOperationForPaidOrder(OrderDto orderDto);
}
