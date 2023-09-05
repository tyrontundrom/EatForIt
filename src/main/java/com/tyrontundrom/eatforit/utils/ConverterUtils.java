package com.tyrontundrom.eatforit.utils;

import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.model.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class ConverterUtils {

    public static DelivererDto convert(Deliverer deliverer) {
        return new DelivererDto().builder()
                .uuid(deliverer.getUuid())
                .personalDataDto(convert(deliverer.getPersonalData()))
                .logginDataDto(convert(deliverer.getLogginData()))
                .archive(deliverer.getArchive())
                .orders(convertOrders(deliverer.getOrders()))
                .build();
    }

    public static Deliverer convert(DelivererDto delivererDto) {
        return new Deliverer().builder()
                .uuid(delivererDto.getUuid())
                .personalData(convert(delivererDto.getPersonalDataDto()))
                .logginData(convert(delivererDto.getLogginDataDto()))
                .archive(delivererDto.getArchive())
                .orders(convertOrdersDto(delivererDto.getOrders()))
                .build();
    }


    public static PersonalData convert(PersonalDataDto personalDataDto) {
        return new PersonalData().builder()
                .name(personalDataDto.getName())
                .surname(personalDataDto.getSurname())
                .sex(personalDataDto.getSex())
                .phone(personalDataDto.getPhone())
                .email(personalDataDto.getEmail())
                .build();
    }

    public static PersonalDataDto convert(PersonalData personalData) {
        return new PersonalDataDto().builder()
                .name(personalData.getName())
                .surname(personalData.getSurname())
                .sex(personalData.getSex())
                .phone(personalData.getPhone())
                .email(personalData.getEmail())
                .build();
    }


    public static LogginData convert(LogginDataDto logginDataDto) {
        return new LogginData().builder()
                .login(logginDataDto.getLogin())
                .password(logginDataDto.getPassword())
                .build();
    }

    public static LogginDataDto convert(LogginData logginData) {
        return new LogginDataDto().builder()
                .login(logginData.getLogin())
                .password(logginData.getPassword())
                .build();
    }


    public static List<Order> convertOrdersDto(List<OrderDto> orderDTOS) {
        if (orderDTOS == null) {
            return new ArrayList<>();
        }
        ArrayList<Order> orders = new ArrayList<>();
        for (OrderDto dto : orderDTOS) {
            orders.add(convert(dto));
        }
        return orders;
    }

    public static Order convert(OrderDto orderDto) {
        return new Order().builder()
                .uuid(orderDto.getUuid())
                .netPrice(orderDto.getNetPrice())
                .grossPrice(orderDto.getGrossPrice())
                .discountCode(convert(orderDto.getDiscountCodedto()))
                .amountToPayGross(orderDto.getAmountToPayGross())
                .description(orderDto.getDescription())
                .orderStatus(convert(orderDto.getOrderStatusDto()))
                .user(convert(orderDto.getUserDto()))
                .deliverer(convert(orderDto.getDelivererDto()))
                .deliveryAddress(convert(orderDto.getDeliveryAddressDto()))
                .orderItem(convertOrderItems(orderDto.getOrderItemDtos()))
                .restaurant(convert(orderDto.getRestaurantDto()))
                .build();
    }

    public static List<OrderDto> convertOrders(List<Order> orders) {
        if (orders == null) {
            return new ArrayList<>();
        }
        ArrayList<OrderDto> orderDTOS = new ArrayList<>();
        for (Order dto : orders) {
            orderDTOS.add(convert(dto));
        }
        return orderDTOS;
    }

    public static OrderDto convert(Order order) {
        return new OrderDto().builder()
                .uuid(order.getUuid())
                .netPrice(order.getNetPrice())
                .grossPrice(order.getGrossPrice())
                .discountCodedto(convert(order.getDiscountCode()))
                .amountToPayGross(order.getAmountToPayGross())
                .description(order.getDescription())
                .orderStatusDto(convert(order.getOrderStatus()))
                .userDto(convert(order.getUser()))
                .delivererDto(convert(order.getDeliverer()))
                .deliveryAddressDto(convert(order.getDeliveryAddress()))
                .orderItemDtos(convertOrderItemDtos(order.getOrderItem()))
                .restaurantDto(convert(order.getRestaurant()))
                .build();
    }


    public static List<DiscountCode> convertDiscountCodeDtos(List<DiscountCodeDto> discountCodeDTOS) {
        if (discountCodeDTOS == null) {
            return new ArrayList<>();
        }
        ArrayList<DiscountCode> discountCodes = new ArrayList<>();
        for (DiscountCodeDto dto : discountCodeDTOS) {
            discountCodes.add(convert(dto));
        }
        return discountCodes;
    }

    public static DiscountCode convert(@Nullable DiscountCodeDto discountCodeDto) {
        if (discountCodeDto == null) {
            return null;
        }
        return new DiscountCode().builder()
                .uuid(discountCodeDto.getUuid())
                .code(discountCodeDto.getCode())
                .discount(discountCodeDto.getDiscount())
                .discountUnit(discountCodeDto.getDiscountUnit())
                .period(convert(discountCodeDto.getPeriodDto()))
                .users(convertUsers(discountCodeDto.getUserDtos()))
                .restaurants(convertRestaurants(discountCodeDto.getRestaurantDtos()))
                .build();
    }

    public static List<DiscountCodeDto> convertDiscountCodes(List<DiscountCode> discountCodes) {
        if (discountCodes == null) {
            return new ArrayList<>();
        }
        ArrayList<DiscountCodeDto> discountCodeDTOS = new ArrayList<>();
        for (DiscountCode dto : discountCodes) {
            discountCodeDTOS.add(convert(dto));
        }
        return discountCodeDTOS;
    }

    public static DiscountCodeDto convert(@Nullable DiscountCode discountCode) {
        if (discountCode == null) {
            return null;
        }
        return new DiscountCodeDto().builder()
                .uuid(discountCode.getUuid())
                .code(discountCode.getCode())
                .discount(discountCode.getDiscount())
                .discountUnit(discountCode.getDiscountUnit())
                .periodDto(convert(discountCode.getPeriod()))
                .userDtos(convertUserDtos(discountCode.getUsers()))
                .restaurantDtos(convertRestaurantDtos(discountCode.getRestaurants()))
                .build();
    }


    public static Period convert(PeriodDto periodDto) {
        return new Period().builder()
                .begin(periodDto.getBegin())
                .end(periodDto.getEnd())
                .build();
    }

    public static PeriodDto convert(Period period) {
        return new PeriodDto().builder()
                .begin(period.getBegin())
                .end(period.getEnd())
                .build();
    }


    public static PeriodTime convert(PeriodTimeDto periodTimeDto) {
        return new PeriodTime().builder()
                .begin(periodTimeDto.getBegin())
                .end(periodTimeDto.getEnd())
                .build();
    }

    public static PeriodTimeDto convert(PeriodTime periodTime) {
        return new PeriodTimeDto().builder()
                .begin(periodTime.getBegin())
                .end(periodTime.getEnd())
                .build();
    }


    public static OrderStatus convert(OrderStatusDto orderStatusDto) {
        return new OrderStatus().builder()
                .isPaid(orderStatusDto.getIsPaid())
                .orderTime(orderStatusDto.getOrderTime())
                .giveOutTime(orderStatusDto.getGiveOutTime())
                .deliveryTime(orderStatusDto.getDeliveryTime())
                .build();
    }

    public static OrderStatusDto convert(OrderStatus orderStatus) {
        return new OrderStatusDto().builder()
                .isPaid(orderStatus.getIsPaid())
                .orderTime(orderStatus.getOrderTime())
                .giveOutTime(orderStatus.getGiveOutTime())
                .deliveryTime(orderStatus.getDeliveryTime())
                .build();
    }


    public static List<User> convertUsers(List<UserDto> userDtos) {
        if (userDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<User> users = new ArrayList<>();
        for (UserDto dto : userDtos) {
            users.add(convert(dto));
        }
        return users;
    }

    public static User convert(UserDto userDto) {
        return new User().builder()
                .uuid(userDto.getUuid())
                .personalData(convert(userDto.getPersonalDatadto()))
                .deliveryAddresses(convertAddresses(userDto.getDeliveryAddressDtos()))
                .logginData(convert(userDto.getLogginDataDto()))
                .orders(convertOrdersDto(userDto.getOrderDtos()))
                .operationEvidences(convertOperationEvidenceDtos(userDto.getOperationEvidenceDtos()))
                .discountCodes(convertDiscountCodeDtos(userDto.getDiscountCodeDtos()))
                .archive(userDto.getArchive())
                .build();
    }

    public static User convertNoEvidence(UserDto userDto) {
        return new User().builder()
                .uuid(userDto.getUuid())
                .build();
    }

    public static List<UserDto> convertUserDtos(List<User> users) {
        if (users == null) {
            return new ArrayList<>();
        }
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (User dto : users) {
            userDtos.add(convert(dto));
        }
        return userDtos;
    }

    public static UserDto convert(User user) {
        return new UserDto().builder()
                .uuid(user.getUuid())
                .personalDatadto(convert(user.getPersonalData()))
                .deliveryAddressDtos(convertAddressDtos(user.getDeliveryAddresses()))
                .logginDataDto(convert(user.getLogginData()))
                .orderDtos(convertOrders(user.getOrders()))
                .operationEvidenceDtos(convertOperationEvidences(user.getOperationEvidences()))
                .discountCodeDtos(convertDiscountCodes(user.getDiscountCodes()))
                .archive(user.getArchive())
                .build();
    }


    public static Address convert(AddressDto DaddressDto) {
        return new Address().builder()
                .street(DaddressDto.getStreet())
                .streetNumber(DaddressDto.getStreetNumber())
                .localNumber(DaddressDto.getLocalNumber())
                .postalCode(DaddressDto.getPostalCode())
                .city(DaddressDto.getCity())
                .borough(DaddressDto.getBorough())
                .state(DaddressDto.getState())
                .build();
    }

    public static AddressDto convert(Address address) {
        return new AddressDto().builder()
                .street(address.getStreet())
                .streetNumber(address.getStreetNumber())
                .localNumber(address.getLocalNumber())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .borough(address.getBorough())
                .state(address.getState())
                .build();
    }


    public static List<DeliveryAddress> convertAddresses(List<DeliveryAddressDto> deliveryAddressDtos) {
        if (deliveryAddressDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<DeliveryAddress> deliveryAddresses = new ArrayList<>();
        for (DeliveryAddressDto dto : deliveryAddressDtos) {
            deliveryAddresses.add(convert(dto));
        }
        return deliveryAddresses;
    }

    public static DeliveryAddress convert(DeliveryAddressDto deliveryAddressDto) {
        return new DeliveryAddress().builder()
                .uuid(deliveryAddressDto.getUuid())
                .description(deliveryAddressDto.getDescription())
                .street(deliveryAddressDto.getStreet())
                .streetNumber(deliveryAddressDto.getStreetNumber())
                .localNumber(deliveryAddressDto.getLocalNumber())
                .postalCode(deliveryAddressDto.getPostalCode())
                .city(deliveryAddressDto.getCity())
                .borough(deliveryAddressDto.getBorough())
                .country(deliveryAddressDto.getCountry())
                .state(deliveryAddressDto.getState())
                .user(convert(deliveryAddressDto.getUserDto()))
                .build();
    }

    public static List<DeliveryAddressDto> convertAddressDtos(List<DeliveryAddress> deliveryAddresses) {
        if (deliveryAddresses == null) {
            return new ArrayList<>();
        }
        ArrayList<DeliveryAddressDto> deliveryAddressDTOS = new ArrayList<>();
        for (DeliveryAddress dto : deliveryAddresses) {
            deliveryAddressDTOS.add(convert(dto));
        }
        return deliveryAddressDTOS;
    }

    public static DeliveryAddressDto convert(DeliveryAddress deliveryAddress) {
        return new DeliveryAddressDto().builder()
                .uuid(deliveryAddress.getUuid())
                .description(deliveryAddress.getDescription())
                .street(deliveryAddress.getStreet())
                .streetNumber(deliveryAddress.getStreetNumber())
                .localNumber(deliveryAddress.getLocalNumber())
                .postalCode(deliveryAddress.getPostalCode())
                .city(deliveryAddress.getCity())
                .borough(deliveryAddress.getBorough())
                .country(deliveryAddress.getCountry())
                .state(deliveryAddress.getState())
                .userDto(convert(deliveryAddress.getUser()))
                .build();
    }


    public static List<OrderItem> convertOrderItems(List<OrderItemDto> orderItemDtos) {
        if (orderItemDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto dto : orderItemDtos) {
            orderItems.add(convert(dto));
        }
        return orderItems;
    }

    public static OrderItem convert(OrderItemDto orderItemDto) {
        return new OrderItem().builder()
                .uuid(orderItemDto.getUuid())
                .quantity(orderItemDto.getQuantity())
                .menuItem(convert(orderItemDto.getMenuItemDto()))
                .build();
    }

    public static List<OrderItemDto> convertOrderItemDtos(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return new ArrayList<>();
        }
        ArrayList<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItem dto : orderItems) {
            orderItemDtos.add(convert(dto));
        }
        return orderItemDtos;
    }

    public static OrderItemDto convert(OrderItem orderItem) {
        return new OrderItemDto().builder()
                .uuid(orderItem.getUuid())
                .quantity(orderItem.getQuantity())
                .menuItemDto(convert(orderItem.getMenuItem()))
                .build();
    }


    public static List<Restaurant> convertRestaurants(List<RestaurantDto> restaurantDtos) {
        if (restaurantDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (RestaurantDto dto : restaurantDtos) {
            restaurants.add(convert(dto));
        }
        return restaurants;
    }

    public static Restaurant convert(RestaurantDto restaurantDto) {
        return new Restaurant().builder()
                .uuid(restaurantDto.getUuid())
                .name(restaurantDto.getName())
                .logginData(convert(restaurantDto.getLogginDataDto()))
                .companyData(convert(restaurantDto.getCompanyDataDto()))
                .opentimes(convertOpenTimeDtos(restaurantDto.getOpenTimeDtos()))
                .orders(convertOrdersDto(restaurantDto.getOrderDtos()))
                .menuItems(convertMenuItemDtos(restaurantDto.getMenuItemDtos()))
                .discountCodes(convertDiscountCodeDtos(restaurantDto.getDiscountCodeDtos()))
                .archive(restaurantDto.getArchive())
                .build();
    }

    public static List<RestaurantDto> convertRestaurantDtos(List<Restaurant> restaurantDtos) {
        if (restaurantDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<RestaurantDto> restaurants = new ArrayList<>();
        for (Restaurant dto : restaurantDtos) {
            restaurants.add(convert(dto));
        }
        return restaurants;
    }

    public static RestaurantDto convert(Restaurant restaurant) {
        return new RestaurantDto().builder()
                .uuid(restaurant.getUuid())
                .name(restaurant.getName())
                .logginDataDto(convert(restaurant.getLogginData()))
                .companyDataDto(convert(restaurant.getCompanyData()))
                .openTimeDtos(convertOpenTimes(restaurant.getOpentimes()))
                .orderDtos(convertOrders(restaurant.getOrders()))
                .menuItemDtos(convertMenuItems(restaurant.getMenuItems()))
                .discountCodeDtos(convertDiscountCodes(restaurant.getDiscountCodes()))
                .archive(restaurant.getArchive())
                .build();
    }


    public static List<OperationEvidence> convertOperationEvidenceDtos(List<OperationEvidenceDto> operationEvidenceDtos) {
        if (operationEvidenceDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<OperationEvidence> operationEvidences = new ArrayList<>();
        for (OperationEvidenceDto dto : operationEvidenceDtos) {
            operationEvidences.add(convert(dto));
        }
        return operationEvidences;
    }

    public static OperationEvidence convert(OperationEvidenceDto operationEvidenceDto) {
        return new OperationEvidence().builder()
                .date(operationEvidenceDto.getDate())
                .evidenceType(operationEvidenceDto.getEvidenceType())
                .amount(operationEvidenceDto.getAmount())
                .user(convertNoEvidence(operationEvidenceDto.getUserDto()))
                .build();
    }

    public static List<OperationEvidenceDto> convertOperationEvidences(List<OperationEvidence> operationEvidences) {
        if (operationEvidences == null) {
            return new ArrayList<>();
        }
        ArrayList<OperationEvidenceDto> operationEvidenceDtos = new ArrayList<>();
        for (OperationEvidence dto : operationEvidences) {
            operationEvidenceDtos.add(convert(dto));
        }
        return operationEvidenceDtos;
    }

    public static OperationEvidenceDto convert(OperationEvidence operationEvidence) {
        return new OperationEvidenceDto().builder()
                .date(operationEvidence.getDate())
                .evidenceType(operationEvidence.getEvidenceType())
                .amount(operationEvidence.getAmount())
                .userDto(convert(operationEvidence.getUser()))
                .build();
    }


    public static List<MenuItem> convertMenuItemDtos(List<MenuItemDto> menuItemDtos) {
        if (menuItemDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for (MenuItemDto dto : menuItemDtos) {
            menuItems.add(convert(dto));
        }
        return menuItems;
    }

    public static MenuItem convert(MenuItemDto menuItemDto) {
        return new MenuItem().builder()
                .uuid(menuItemDto.getUuid())
                .name(menuItemDto.getName())
                .netPrice(menuItemDto.getNetPrice())
                .vatTax(menuItemDto.getVatTax())
                .grossPrice(menuItemDto.getGrossPrice())
                .dishes(convertDishDtos(menuItemDto.getDishDtos()))
                .restaurant(convert(menuItemDto.getRestaurantDto()))
                .build();
    }

    public static List<MenuItemDto> convertMenuItems(List<MenuItem> menuItems) {
        if (menuItems == null) {
            return new ArrayList<>();
        }
        ArrayList<MenuItemDto> menuItemDtos = new ArrayList<>();
        for (MenuItem dto : menuItems) {
            menuItemDtos.add(convert(dto));
        }
        return menuItemDtos;
    }

    public static MenuItemDto convert(MenuItem menuItem) {
        return new MenuItemDto().builder()
                .uuid(menuItem.getUuid())
                .name(menuItem.getName())
                .netPrice(menuItem.getNetPrice())
                .vatTax(menuItem.getVatTax())
                .grossPrice(menuItem.getGrossPrice())
                .dishDtos(convertDishes(menuItem.getDishes()))
                .restaurantDto(convert(menuItem.getRestaurant()))
                .build();
    }


    public static CompanyData convert(CompanyDataDto companyDataDto) {
        return new CompanyData().builder()
                .name(companyDataDto.getName())
                .address(convert(companyDataDto.getAddressDto()))
                .NIP(companyDataDto.getNIP())
                .phone(companyDataDto.getPhone())
                .email(companyDataDto.getEmail())
                .build();
    }

    public static CompanyDataDto convert(CompanyData companyData) {
        return new CompanyDataDto().builder()
                .name(companyData.getName())
                .addressDto(convert(companyData.getAddress()))
                .NIP(companyData.getNIP())
                .phone(companyData.getPhone())
                .email(companyData.getEmail())
                .build();
    }


    public static List<OpenTime> convertOpenTimeDtos(List<OpenTimeDto> openTimeDtos) {
        if (openTimeDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<OpenTime> openTimes = new ArrayList<>();
        for (OpenTimeDto dto : openTimeDtos) {
            openTimes.add(convert(dto));
        }
        return openTimes;
    }

    public static OpenTime convert(OpenTimeDto openTimeDto) {
        return new OpenTime().builder()
                .uuid(openTimeDto.getUuid())
                .dayOfWeek(openTimeDto.getDayOfWeek())
                .periodTime(convert(openTimeDto.getPeriodTimeDto()))
                .restaurant(convert(openTimeDto.getRestaurantDto()))
                .build();
    }

    public static List<OpenTimeDto> convertOpenTimes(List<OpenTime> openTimes) {
        if (openTimes == null) {
            return new ArrayList<>();
        }
        ArrayList<OpenTimeDto> openTimeDtos = new ArrayList<>();
        for (OpenTime dto : openTimes) {
            openTimeDtos.add(convert(dto));
        }
        return openTimeDtos;
    }

    public static OpenTimeDto convert(OpenTime openTime) {
        return new OpenTimeDto().builder()
                .uuid(openTime.getUuid())
                .dayOfWeek(openTime.getDayOfWeek())
                .periodTimeDto(convert(openTime.getPeriodTime()))
                .restaurantDto(convert(openTime.getRestaurant()))
                .build();
    }


    public static List<Dish> convertDishDtos(List<DishDto> dishDtos) {
        if (dishDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<Dish> dishes = new ArrayList<>();
        for (DishDto dto : dishDtos) {
            dishes.add(convert(dto));
        }
        return dishes;
    }

    public static Dish convert(@Nullable DishDto dishDto) {
        if (dishDto == null) {
            return null;
        }
        return new Dish().builder()
                .uuid(dishDto.getUuid())
                .quantity(dishDto.getQuantity())
                .product(convert(dishDto.getProductDto()))
                .menuItems(convertMenuItemDtos(dishDto.getMenuItemDtos()))
                .build();
    }

    public static List<DishDto> convertDishes(List<Dish> dishes) {
        if (dishes == null) {
            return new ArrayList<>();
        }
        ArrayList<DishDto> dishDtos = new ArrayList<>();
        for (Dish dto : dishes) {
            dishDtos.add(convert(dto));
        }
        return dishDtos;
    }

    public static DishDto convert(@Nullable Dish dish) {
        if (dish == null) {
            return null;
        }
        return new DishDto().builder()
                .uuid(dish.getUuid())
                .quantity(dish.getQuantity())
                .productDto(convert(dish.getProduct()))
                .menuItemDtos(convertMenuItems(dish.getMenuItems()))
                .build();
    }


    public static Product convert(ProductDto productDto) {
        return new Product().builder()
                .uuid(productDto.getUuid())
                .name(productDto.getName())
                .ingredients(convertIngredientDtos(productDto.getIngredientDtos()))
                .dish(convert(productDto.getDishDto()))
                .build();
    }

    public static ProductDto convert(Product product) {
        return new ProductDto().builder()
                .uuid(product.getUuid())
                .name(product.getName())
                .ingredientDtos(convertIngredients(product.getIngredients()))
                .dishDto(convert(product.getDish()))
                .build();
    }


    public static List<Ingredient> convertIngredientDtos(List<IngredientDto> ingredientDtos) {
        if (ingredientDtos == null) {
            return new ArrayList<>();
        }
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDto dto : ingredientDtos) {
            ingredients.add(convert(dto));
        }
        return ingredients;
    }

    public static Ingredient convert(IngredientDto ingredientDto) {
        return new Ingredient().builder()
                .uuid(ingredientDto.getUuid())
                .name(ingredientDto.getName())
                .isAllergen(ingredientDto.getIsAllergen())
                .build();
    }

    public static List<IngredientDto> convertIngredients(List<Ingredient> ingredients) {
        if (ingredients == null) {
            return new ArrayList<>();
        }
        ArrayList<IngredientDto> ingredientDtos = new ArrayList<>();
        for (Ingredient dto : ingredients) {
            ingredientDtos.add(convert(dto));
        }
        return ingredientDtos;
    }

    public static IngredientDto convert(Ingredient ingredient) {
        return new IngredientDto().builder()
                .uuid(ingredient.getUuid())
                .name(ingredient.getName())
                .isAllergen(ingredient.getIsAllergen())
                .build();
    }


    public static Employee convert(EmployeeDto employeeDto) {
        return new Employee().builder()
                .uuid(employeeDto.getUuid())
                .personalData(convert(employeeDto.getPersonalDataDto()))
                .logginData(convert(employeeDto.getLogginDataDto()))
                .archive(employeeDto.getArchive())
                .build();
    }

    public static EmployeeDto convert(Employee employee) {
        return new EmployeeDto().builder()
                .uuid(employee.getUuid())
                .personalDataDto(convert(employee.getPersonalData()))
                .logginDataDto(convert(employee.getLogginData()))
                .archive(employee.getArchive())
                .build();
    }
}
