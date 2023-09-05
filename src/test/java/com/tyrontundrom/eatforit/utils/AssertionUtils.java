package com.tyrontundrom.eatforit.utils;

import com.google.common.collect.Maps;
import com.tyrontundrom.eatforit.dto.*;
import org.junit.jupiter.api.Assertions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class AssertionUtils {

    public static void assertEquals(DelivererDto expected, DelivererDto actual) {
        Assertions.assertEquals(expected.getUuid(), actual.getUuid());
        assertEquals(expected.getPersonalDataDto(), actual.getPersonalDataDto());
        assertEquals(expected.getLogginDataDto(), actual.getLogginDataDto());
        Assertions.assertEquals(expected.getArchive(), actual.getArchive());

        Map<UUID, OrderDto> expectedOrders = Maps.uniqueIndex(expected.getOrders() != null ? expected.getOrders() : new ArrayList<>(), OrderDto::getUuid);
        Map<UUID, OrderDto> actualOrders = Maps.uniqueIndex(actual.getOrders() != null ? actual.getOrders() : new ArrayList<>(), OrderDto::getUuid);
        Assertions.assertEquals(expectedOrders.keySet(), actualOrders.keySet());
    }

    public static void assertEquals(PersonalDataDto expected, PersonalDataDto actual) {
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getSurname(), actual.getSurname());
        Assertions.assertEquals(expected.getSex(), actual.getSex());
        Assertions.assertEquals(expected.getPhone(), actual.getPhone());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
    }

    public static void assertEquals(LogginDataDto expected, LogginDataDto actual) {
        Assertions.assertEquals(expected.getLogin(), actual.getLogin());
        Assertions.assertEquals(expected.getPassword(), actual.getPassword());
    }

    public static void assertEquals(DeliveryAddressDto expected, DeliveryAddressDto actual) {
        Assertions.assertEquals(expected.getUuid(), actual.getUuid());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getStreet(), actual.getStreet());
        Assertions.assertEquals(expected.getStreetNumber(), actual.getStreetNumber());
        Assertions.assertEquals(expected.getLocalNumber(), actual.getLocalNumber());
        Assertions.assertEquals(expected.getPostalCode(), actual.getPostalCode());
        Assertions.assertEquals(expected.getCity(), actual.getCity());
        Assertions.assertEquals(expected.getBorough(), actual.getBorough());
        Assertions.assertEquals(expected.getCountry(), actual.getCountry());
        Assertions.assertEquals(expected.getState(), actual.getState());
        assertEqualsId(expected.getUserDto(), actual.getUserDto());
    }

    public static void assertEquals(DiscountCodeDto expected, DiscountCodeDto actual) {
        Assertions.assertEquals(expected.getUuid(), actual.getUuid());
        Assertions.assertEquals(expected.getCode(), actual.getCode());
        Assertions.assertEquals(expected.getDiscount(), actual.getDiscount());
        Assertions.assertEquals(expected.getDiscount(), actual.getDiscount());
        assertEquals(expected.getPeriodDto(), actual.getPeriodDto());
    }

    public static void assertEquals(PeriodDto expected, PeriodDto actual) {
        Assertions.assertEquals(expected.getBegin(), actual.getBegin());
        Assertions.assertEquals(expected.getEnd(), actual.getEnd());
    }

    public static void assertEquals(OperationEvidenceDto expected, OperationEvidenceDto actual) {
        Assertions.assertEquals(expected.getDate(), actual.getDate());
        Assertions.assertEquals(expected.getEvidenceType(), actual.getEvidenceType());
        Assertions.assertEquals(expected.getAmount(), actual.getAmount());
        assertEqualsId(expected.getUserDto(), actual.getUserDto());
    }

    public static void assertEquals(OrderDto expected, OrderDto actual, OrderStatusType orderStatusType) {
        Assertions.assertEquals(expected.getUuid(), actual.getUuid());
        Assertions.assertEquals(expected.getNetPrice(), actual.getNetPrice());
        Assertions.assertEquals(expected.getGrossPrice(), actual.getGrossPrice());
        assertEqualsId(expected.getDiscountCodedto(), actual.getDiscountCodedto());
        Assertions.assertEquals(expected.getAmountToPayGross(), actual.getAmountToPayGross());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getOrderStatusDto(), actual.getOrderStatusDto(), orderStatusType);
        assertEqualsId(expected.getUserDto(), actual.getUserDto());
        assertEqualsId(expected.getRestaurantDto(), actual.getRestaurantDto());
    }

    public enum OrderStatusType {
        NEW, PAID, GIVED_OUT, DELIVERED;
    }

    private static void assertEquals(OrderStatusDto expected, OrderStatusDto actual, OrderStatusType orderStatus) {
        Assertions.assertNotNull(actual.getOrderTime());
        switch (orderStatus) {
            case NEW: break;
            case PAID: assert actual.getIsPaid(); break;
            case GIVED_OUT:
                assert expected != null;
                assert actual.getIsPaid();
                Assertions.assertEquals(actual.getGiveOutTime(), expected.getGiveOutTime());
                break;
            case DELIVERED:
                assert expected != null;
                assert actual.getIsPaid();
                Assertions.assertEquals(actual.getGiveOutTime(), expected.getGiveOutTime());
                Assertions.assertEquals(actual.getDeliveryTime(), expected.getDeliveryTime());
                break;
        }
    }

    private static void assertEqualsId(UserDto expected, UserDto actual) {
        Assertions.assertEquals(expected.getUuid(), actual.getUuid());
    }

    private static void assertEqualsId(@Nullable DiscountCodeDto expected, @Nullable DiscountCodeDto actual) {
        Assertions.assertEquals(expected != null ? expected.getUuid() : null, actual != null ? actual.getUuid() : null);
    }

    private static void assertEqualsId(RestaurantDto expected, RestaurantDto actual) {
        Assertions.assertEquals(expected.getUuid(), actual.getUuid());
    }
}
