package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.events.OperationEvidenceCreator;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.OrderService;
import com.tyrontundrom.eatforit.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Validated
@Controller
@RequestMapping(path = "api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class OrderController {

    private final OrderService orderService;
    private final DelivererService delivererService;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    interface OrderListView extends OrderDto.View.Basic, UserDto.View.Id, DelivererDto.View.Id, RestaurantDto.View.Id {}
    interface OrderView extends OrderDto.View.Extended, UserDto.View.Id, DelivererDto.View.Id, RestaurantDto.View.Id {}

    interface OrderDataUpdateValidation extends Default, OrderDto.OrderValidation {}
    interface OrderStatusValidation extends Default, OrderDto.OrderStatusValidation {}
    interface GiveOutValidation extends Default, OrderDto.OrderStatusValidation, OrderStatusDto.GivedOutStatusValidation {}
    interface DeliveryValidation extends Default, OrderDto.OrderStatusValidation, OrderStatusDto.DeliveryValidation {}

    @JsonView(OrderListView.class)
    @GetMapping
    public List<OrderDto> getAll() {
        return orderService.getAll();
    }

    @JsonView(OrderListView.class)
    @GetMapping(path = {"user"})
    public List<OrderDto> getByUser(@RequestParam("user") UUID userUuid) {
        UserDto userDto = userService.getByUuid(userUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return userDto.getOrderDtos();
    }

    @JsonView(OrderListView.class)
    @GetMapping(path = {"deliverer"})
    public List<OrderDto> getByDeliverer(@RequestParam("deliverer") UUID delivererUuid) {
        DelivererDto delivererDto = delivererService.getByUuid(delivererUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return delivererDto.getOrders();
    }

    @JsonView(OrderView.class)
    @GetMapping("{uuid}")
    public OrderDto get(@PathVariable UUID uuid) {
        return orderService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Validated(OrderDataUpdateValidation.class)
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid OrderDto orderDto) {
        orderService.put(uuid, orderDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        orderService.delete(uuid);
    }

    @Transactional
    @Validated(OrderStatusValidation.class)
    @PatchMapping("{uuid}/paid")
    public void patchIsPaid(@PathVariable UUID uuid) {
        OrderDto orderDto = orderService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderService.setIsPaid(orderDto);

        OperationEvidenceCreator operationEvidenceCreator = new OperationEvidenceCreator(
                this, orderService.newOperationForPaidOrder(orderDto));
        applicationEventPublisher.publishEvent(operationEvidenceCreator);
    }

    @Transactional
    @Validated(GiveOutValidation.class)
    @PatchMapping("{uuid}/paid")
    public void patchIsGiveOut(@PathVariable UUID uuid, @RequestBody @Valid OrderStatusDto orderStatusDto) {
        orderService.setIsGivedOut(uuid, orderStatusDto);
    }

    @Transactional
    @Validated(DeliveryValidation.class)
    @PatchMapping("{uuid}/paid")
    public void patchIsDelivered(@PathVariable UUID uuid, @RequestBody @Valid OrderStatusDto orderStatusDto) {
        orderService.setIsDelivered(uuid, orderStatusDto);
    }
}
