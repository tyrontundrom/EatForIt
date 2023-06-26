package com.tyrontundrom.eatforit.controller;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.OrderDto;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.OrderService;
import com.tyrontundrom.eatforit.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class OrderController {

    private final OrderService orderService;
    private final DelivererService delivererService;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping
    public List<OrderDto> getAll() {
        return null;
    }

    @GetMapping("{uuid}")
    public OrderDto get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody OrderDto orderDto) {

    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {

    }
}
