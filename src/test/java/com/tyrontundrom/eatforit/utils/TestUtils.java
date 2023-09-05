package com.tyrontundrom.eatforit.utils;

import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.model.*;
import com.tyrontundrom.eatforit.model.enums.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static DelivererDto delivererDto(String uuid, PersonalDataDto personalDataDto, LogginDataDto logginDataDto, Archive archive) {
        return new DelivererDto().builder()
                .uuid(UUID.fromString(uuid))
                .personalDataDto(personalDataDto)
                .logginDataDto(logginDataDto)
                .archive(archive)
                .orders(new ArrayList<>())
                .build();
    }

    public static PersonalDataDto personalDataDto(String name, String surname, Sex sex, String phone, String email) {
        return new PersonalDataDto().builder()
                .name(name)
                .surname(surname)
                .sex(sex)
                .phone(phone)
                .email(email)
                .build();
    }

    public static LogginDataDto logginDataDto(String login, String password) {
        return new LogginDataDto().builder()
                .login(login)
                .password(password)
                .build();
    }

    public static DeliveryAddressDto deliveryAddressDto(String uuid, String description, String street, String streetNumber,
                                                        String locaNumber, String postcode, String city, String borough,
                                                        String country, String state, UserDto userDto) {
        return new DeliveryAddressDto().builder()
                .uuid(UUID.fromString(uuid))
                .description(description)
                .street(street)
                .streetNumber(streetNumber)
                .localNumber(locaNumber)
                .postalCode(postcode)
                .city(city)
                .borough(borough)
                .country(country)
                .state(state)
                .userDto(userDto)
                .build();
    }

    public static UserDto userDto(String uuid, PersonalDataDto personalDataDto, List<DeliveryAddressDto> addresses,
                                  LogginDataDto logginDataDto, Archive archive) {
        return new UserDto().builder()
                .uuid(UUID.fromString(uuid))
                .personalDatadto(personalDataDto)
                .deliveryAddressDtos(addresses)
                .logginDataDto(logginDataDto)
                .operationEvidenceDtos(new ArrayList<>())
                .archive(archive)
                .build();
    }

    public static DiscountCodeDto discountCodeDto(String uuid, String code, BigDecimal discount, DiscountUnit unit, String begin,
                                                  String end, List<UserDto>  userDtos, List<RestaurantDto> restaurantDtos) {
        return new DiscountCodeDto().builder()
                .uuid(UUID.fromString(uuid))
                .code(code)
                .discount(discount)
                .discountUnit(unit)
                .periodDto(periodDto(begin, end))
                .userDtos(userDtos)
                .restaurantDtos(restaurantDtos)
                .build();
    }

    public static DiscountCode discountCode(String uuid, String code, BigDecimal discount, DiscountUnit unit, String begin,
                                            String end, List<User> users, List<Restaurant> restaurants) {
        return new DiscountCode().builder()
                .uuid(UUID.fromString(uuid))
                .code(code)
                .discount(discount)
                .discountUnit(unit)
                .period(period(begin, end))
                .users(users)
                .restaurants(restaurants)
                .build();
    }

    public static OperationEvidenceDto operationEvidenceDto(String dateInstant, EvidenceType evidenceType,
                                                            BigDecimal amount, UserDto userDto) {
        return new OperationEvidenceDto().builder()
                .date(Instant.parse(dateInstant))
                .evidenceType(evidenceType)
                .amount(amount)
                .userDto(userDto)
                .build();
    }

    public static PeriodDto periodDto(String begin, String end) {
        return new PeriodDto().builder()
                .begin(begin != null ? LocalDateTime.parse(begin) : null)
                .end(end != null ? LocalDateTime.parse(end) : null)
                .build();
    }

    public static Period period(String begin, String end) {
        return new Period().builder()
                .begin(begin != null ? LocalDateTime.parse(begin) : null)
                .end(end != null ? LocalDateTime.parse(end) : null)
                .build();
    }

    public static User user(String uuid, PersonalData personalData, List<DeliveryAddress> addresses, LogginData logginData, Archive archive) {
        return new User().builder()
                .uuid(UUID.fromString(uuid))
                .personalData(personalData)
                .deliveryAddresses(addresses)
                .logginData(logginData)
                .operationEvidences(new ArrayList<>())
                .archive(archive)
                .build();
    }

    public static OperationEvidence operationEvidence(String dateInstant, EvidenceType evidenceType, BigDecimal amount, User user) {
        return new OperationEvidence().builder()
                .date(Instant.parse(dateInstant))
                .evidenceType(evidenceType)
                .amount(amount)
                .user(user)
                .build();
    }

    public static Deliverer deliverer(String uuid, PersonalData personalData, LogginData logginData, Archive archive) {
        return new Deliverer().builder()
                .uuid(UUID.fromString(uuid))
                .personalData(personalData)
                .logginData(logginData)
                .archive(archive)
                .orders(new ArrayList<>())
                .build();
    }

    public static PersonalData personalData(String name, String surname, Sex sex, String phone, String email) {
        return new PersonalData().builder()
                .name(name)
                .surname(surname)
                .sex(sex)
                .phone(phone)
                .email(email)
                .build();
    }

    public static LogginData logginData(String login, String passwrd) {
        return new LogginData().builder()
                .login(login)
                .password(passwrd)
                .build();
    }

    public static DeliveryAddress deliveryAddress(String uuid, String description, String street, String streetNumber,
                                                  String locaNumber, String postcode, String city, String borough,
                                                  String country, String state, User user) {
        return new DeliveryAddress().builder()
                .uuid(UUID.fromString(uuid))
                .description(description)
                .street(street)
                .streetNumber(streetNumber)
                .localNumber(locaNumber)
                .postalCode(postcode)
                .city(city)
                .borough(borough)
                .country(country)
                .state(state)
                .user(user)
                .build();
    }

    public static Restaurant restaurant(String uuid, String name, LogginData logginData, CompanyData companyData, Archive archive) {
        return new Restaurant().builder()
                .uuid(UUID.fromString(uuid))
                .name(name)
                .logginData(logginData)
                .companyData(companyData)
                .archive(archive)
                .orders(new ArrayList<>())
                .opentimes(new ArrayList<>())
                .menuItems(new ArrayList<>())
                .discountCodes(new ArrayList<>())
                .build();
    }

    public static CompanyData companyData(String name, Address address, String NIP, String REGON, String phone, String email) {
        return new CompanyData().builder()
                .name(name)
                .address(address)
                .NIP(NIP)
                .REGON(REGON)
                .phone(phone)
                .email(email)
                .build();
    }

    public static Address address(String street, String streetNumber, String postcode, String city) {
        return new Address().builder()
                .street(street)
                .streetNumber(streetNumber)
                .postalCode(postcode)
                .city(city)
                .build();
    }

    public static Order order(String uuid, DiscountCode discountCode, String description, User user,
                              Deliverer deliverer, DeliveryAddress deliveryAddress, Restaurant restaurant, BigDecimal netPrice,
                              BigDecimal grossPrice, BigDecimal amountToPayGross, OrderStatus orderStatus, OrderItem... orderItems) {
        return new Order().builder()
                .uuid(UUID.fromString(uuid))
                .discountCode(discountCode)
                .description(description)
                .user(user)
                .deliverer(deliverer)
                .deliveryAddress(deliveryAddress)
                .restaurant(restaurant)
                .orderItem(Arrays.asList(orderItems))
                .netPrice(netPrice)
                .grossPrice(grossPrice)
                .amountToPayGross(amountToPayGross)
                .orderStatus(orderStatus)
                .build();
    }

    public static OrderStatus orderStatus(@Nullable String orderTimeInstant, Boolean isPaid,
                                          @Nullable String giveOutTimeInstant, @Nullable String deliveryTimeInstant) {
        return new OrderStatus().builder()
                .orderTime(orderTimeInstant != null ? Instant.parse(orderTimeInstant) : null)
                .isPaid(isPaid)
                .giveOutTime(giveOutTimeInstant != null ? Instant.parse(giveOutTimeInstant) : null)
                .deliveryTime(deliveryTimeInstant != null ? Instant.parse(deliveryTimeInstant) : null)
                .build();
    }

    public static OrderStatusDto orderStatusDto(@Nullable String orderTimeInstant, Boolean isPaid,
                                                @Nullable String giveOutTimeInstant, @Nullable String deliveryTimeInstant) {
        return new OrderStatusDto().builder()
                .orderTime(orderTimeInstant != null ? Instant.parse(orderTimeInstant) : null)
                .isPaid(isPaid)
                .giveOutTime(giveOutTimeInstant != null ? Instant.parse(giveOutTimeInstant) : null)
                .deliveryTime(deliveryTimeInstant != null ? Instant.parse(deliveryTimeInstant) : null)
                .build();
    }

    public static OrderDto orderDto(String uuid, DiscountCodeDto discountCodeDto, String descripion, UserDto userDto,
                                    DelivererDto delivererDto, DeliveryAddressDto deliveryAddressDto,
                                    RestaurantDto restaurantDto, OrderItemDto... orderItemDtos) {
        return new OrderDto().builder()
                .uuid(UUID.fromString(uuid))
                .discountCodedto(discountCodeDto)
                .description(descripion)
                .userDto(userDto)
                .delivererDto(delivererDto)
                .deliveryAddressDto(deliveryAddressDto)
                .restaurantDto(restaurantDto)
                .orderItemDtos(Arrays.asList(orderItemDtos))
                .build();
    }

    public static OrderItem orderItem(String uuid, Integer quantity, MenuItem menuItem) {
        return new OrderItem().builder()
                .uuid(UUID.fromString(uuid))
                .quantity(quantity)
                .menuItem(menuItem)
                .build();
    }

    public static MenuItem menuItem(String uuid, String name, BigDecimal netPrice, VatTax vatTax,
                                    BigDecimal grossPrice, Restaurant restaurant, Dish... dishes) {
        return new MenuItem().builder()
                .uuid(UUID.fromString(uuid))
                .name(name)
                .netPrice(netPrice)
                .vatTax(vatTax)
                .grossPrice(grossPrice)
                .restaurant(restaurant)
                .dishes(Arrays.asList(dishes))
                .build();
    }

    public static Dish dish(String uuid, Integer quantity, Product product) {
        return new Dish().builder()
                .uuid(UUID.fromString(uuid))
                .quantity(quantity)
                .product(product)
                .build();
    }

    public static Product product(String uuid, String name, Ingredient... ingredients) {
        return new Product().builder()
                .uuid(UUID.fromString(uuid))
                .name(name)
                .ingredients(Arrays.asList(ingredients))
                .build();
    }

    public static Ingredient ingredient(String uuid, String name, Boolean isAllergen) {
        return new Ingredient().builder()
                .uuid(UUID.fromString(uuid))
                .name(name)
                .isAllergen(isAllergen)
                .build();
    }
}
