package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.dto.OrderDto;
import com.tyrontundrom.eatforit.dto.OrderStatusDto;
import com.tyrontundrom.eatforit.dto.UserDto;
import com.tyrontundrom.eatforit.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class OrderServiceImp implements OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final DelivererJpaRepository delivererJpaRepository;
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final DiscountCodeJpaRepository discountCodeJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public List<OrderDto> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, OrderDto orderDto) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<OrderDto> getByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void setIsPaid(OrderDto orderDto) {

    }

    @Override
    public void setIsDelivered(OrderDto orderDto, OrderStatusDto orderStatusDto) {

    }

    @Override
    public void setIsGivedOut(OrderDto orderDto, OrderStatusDto orderStatusDto) {

    }

    @Override
    public UserDto newOperationForPaidOrder(OrderDto orderDto) {
        return null;
    }
}
