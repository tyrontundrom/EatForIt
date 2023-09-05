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
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderItemServiceImp implements OrderItemService {

    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public List<OrderItem> getAll() {
        return orderItemJpaRepository.findAll();
    }

    @Override
    public void add(OrderItem orderItem) {
        orderItemJpaRepository.save(orderItem);
    }

    @Override
    public void delete(OrderItem orderItem) {
        orderItemJpaRepository.delete(orderItem);
    }

    @Override
    public Optional<OrderItem> getByUuid(UUID uuid) {
        return orderItemJpaRepository.findByUuid(uuid);
    }

    @Override
    public BigDecimal calculatePrice(List<OrderItem> orderItems, BigDecimal startPrice, PriceType priceType) throws UnsupportedDataTypeException {
        BigDecimal orderPrice = startPrice;
        for (OrderItem orderItem : orderItems) {
            switch (priceType) {
                case NET:
                    orderPrice = orderPrice.add(
                            orderItem.getMenuItem().getNetPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                    );
                    break;
                case GROSS:
                    orderPrice = orderPrice.add(
                            orderItem.getMenuItem().getGrossPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                    );
                    break;
                default:
                    throw new UnsupportedDataTypeException();
            }
        }
        return orderPrice;
    }

    @Override
    public BigDecimal applyDiscount(DiscountCode discountCode, BigDecimal orderGrossPrice) throws UnsupportedDataTypeException {
        if (discountCode == null) {
            return orderGrossPrice;
        }

        BigDecimal amountToGrossPay;

        switch (discountCode.getDiscountUnit()) {
            case PLN -> amountToGrossPay = orderGrossPrice.subtract(discountCode.getDiscount());
            case PERCENT -> amountToGrossPay = orderGrossPrice.multiply(
                    BigDecimal.valueOf(100).subtract(discountCode.getDiscount()).divide(
                            BigDecimal.valueOf(100), RoundingMode.HALF_UP));
            default -> throw new UnsupportedDataTypeException();
        }
        return amountToGrossPay;
    }
}
