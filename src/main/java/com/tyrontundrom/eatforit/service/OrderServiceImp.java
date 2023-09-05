package com.tyrontundrom.eatforit.service;

import com.google.common.base.Objects;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.model.*;
import com.tyrontundrom.eatforit.model.enums.EvidenceType;
import com.tyrontundrom.eatforit.model.enums.PriceType;
import com.tyrontundrom.eatforit.repo.*;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import jakarta.activation.UnsupportedDataTypeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderJpaRepository orderJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final DelivererJpaRepository delivererJpaRepository;
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final DiscountCodeJpaRepository discountCodeJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderItemService orderItemService;

    @Override
    public List<OrderDto> getAll() {
        return orderJpaRepository.findAll().stream()
                .map(ConverterUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, OrderDto orderDto) {
        if (!Objects.equal(orderDto.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userJpaRepository.findByUuid(orderDto.getUserDto().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Restaurant restaurant = restaurantJpaRepository.findByUuid(orderDto.getRestaurantDto().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Deliverer deliverer = delivererJpaRepository.findByUuid(orderDto.getDelivererDto().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Order order = orderJpaRepository.findByUuid(orderDto.getUuid())
                .orElseGet(() -> newOrder(uuid, user, restaurant));

        if (!Objects.equal(order.getUser().getUuid(), orderDto.getUserDto().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equal(order.getRestaurant().getUuid(), orderDto.getRestaurantDto().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (order.getOrderStatus().getDeliveryTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<OrderItem> orderItems = putOrderItems(orderDto);
        DiscountCode discountCode = putDiscountCode(orderDto);

        BigDecimal orderNetPrice;
        BigDecimal orderGrossPrice;
        BigDecimal amountToPayGross;
        try {
            orderNetPrice = orderItemService.calculatePrice(orderItems, BigDecimal.ZERO, PriceType.NET);
            orderGrossPrice = orderItemService.calculatePrice(orderItems, BigDecimal.ZERO, PriceType.GROSS);
            amountToPayGross = orderItemService.applyDiscount(discountCode, orderGrossPrice);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.setDescription(order.getDescription());
        order.setNetPrice(orderNetPrice);
        order.setGrossPrice(orderGrossPrice);
        order.setAmountToPayGross(amountToPayGross);
        order.setDiscountCode(discountCode);
        order.setOrderItem(orderItems);
        order.setDeliverer(deliverer);

        if (order.getId() == null) {
            orderJpaRepository.save(order);
        }
    }

    private DiscountCode putDiscountCode(OrderDto orderDto) {
        DiscountCode discountCode = null;
        if (orderDto.getDiscountCodedto() != null) {
            discountCode = discountCodeJpaRepository.findByUuid(orderDto.getDiscountCodedto().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            if (discountCode.getRestaurants() != null) {
                discountCode.getRestaurants().stream()
                        .filter(r -> r.getUuid().equals(orderDto.getRestaurantDto().getUuid()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            }
            if (discountCode.getUsers() != null) {
                discountCode.getUsers().stream()
                        .filter(u -> u.getUuid().equals(orderDto.getUserDto().getUuid()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            }
        }
        return discountCode;
    }

    private List<OrderItem> putOrderItems(OrderDto orderDto) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderDto.getOrderItemDtos()) {
            MenuItem menuItem = menuItemJpaRepository.findByUuid(orderItemDto.getMenuItemDto().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            OrderItem orderItem = orderItemJpaRepository.findByUuid(orderDto.getUuid())
                    .orElseGet(() -> newOrderItem(orderDto.getUuid()));
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setMenuItem(menuItem);
            if (orderItem.getId() == null) {
                orderItemJpaRepository.save(orderItem);
            }
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private OrderItem newOrderItem(UUID uuid) {
        return new OrderItem().builder()
                .uuid(uuid)
                .build();
    }

    private Order newOrder(UUID uuid, User user, Restaurant restaurant) {
        return new Order().builder()
                .uuid(uuid)
                .user(user)
                .restaurant(restaurant)
                .orderStatus(newOrderStatus())
                .build();
    }

    private OrderStatus newOrderStatus() {
        return new OrderStatus().builder()
                .orderTime(Instant.now())
                .isPaid(false)
                .build();
    }

    @Override
    public void delete(UUID uuid) {
        Order order = orderJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderJpaRepository.delete(order);
    }

    @Override
    public Optional<OrderDto> getByUuid(UUID uuid) {
        return orderJpaRepository.findByUuid(uuid).map(ConverterUtils::convert);
    }

    @Override
    public void setIsPaid(OrderDto orderDto) {
        Order order = orderJpaRepository.findByUuid(orderDto.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!order.getOrderStatus().getIsPaid()) {
            order.getOrderStatus().setIsPaid(true);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void setIsDelivered(UUID uuid, OrderStatusDto orderStatusDto) {
        Order order = orderJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!order.getOrderStatus().getIsPaid() || order.getOrderStatus().getGiveOutTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.getOrderStatus().setDeliveryTime(orderStatusDto.getDeliveryTime());
    }

    @Override
    public void setIsGivedOut(UUID uuid, OrderStatusDto orderStatusDto) {
        Order order = orderJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!order.getOrderStatus().getIsPaid() || order.getOrderStatus().getDeliveryTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.getOrderStatus().setGiveOutTime(orderStatusDto.getGiveOutTime());
    }

    @Override
    public UserDto newOperationForPaidOrder(OrderDto orderDto) {
       User user = userJpaRepository.findByUuid(orderDto.getUserDto().getUuid())
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
       UserDto userDto = ConverterUtils.convert(user);
       userDto.setOperationEvidenceDtos(List.of(newEvidenceForOrderPayment(orderDto)));
       return userDto;
    }

    private OperationEvidenceDto newEvidenceForOrderPayment(OrderDto orderDto) {
        return new OperationEvidenceDto().builder()
                .date(Instant.now())
                .userDto(orderDto.getUserDto())
                .amount(orderDto.getAmountToPayGross())
                .evidenceType(EvidenceType.PAYMENT)
                .build();
    }
}
