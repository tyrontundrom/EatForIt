package com.tyrontundrom.eatforit.controller;

import com.google.common.truth.Truth8;
import com.tyrontundrom.eatforit.config.JPAConfiguration;
import com.tyrontundrom.eatforit.dto.OrderDto;
import com.tyrontundrom.eatforit.dto.OrderStatusDto;
import com.tyrontundrom.eatforit.listeners.OperationEvidenceListener;
import com.tyrontundrom.eatforit.model.*;
import com.tyrontundrom.eatforit.model.enums.Archive;
import com.tyrontundrom.eatforit.model.enums.EvidenceType;
import com.tyrontundrom.eatforit.model.enums.Sex;
import com.tyrontundrom.eatforit.model.enums.VatTax;
import com.tyrontundrom.eatforit.repo.*;
import com.tyrontundrom.eatforit.service.*;
import com.tyrontundrom.eatforit.utils.AssertionUtils;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import com.tyrontundrom.eatforit.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(classes = {
        JPAConfiguration.class,
        OrderControllerTest.Config.class
})
class OrderControllerTest {

    @Configuration
    public static class Config {

        @Bean
        public OperationEvidenceService operationEvidenceService(OperationEvidenceJpaRepository operationEvidenceJpaRepository) {
            return new OperationEvidenceServiceImp(operationEvidenceJpaRepository);
        }

        @Bean
        public OperationEvidenceListener operationEvidenceListener(OperationEvidenceService operationEvidenceService, UserJpaRepository userJpaRepository) {
            return new OperationEvidenceListener(operationEvidenceService, userJpaRepository);
        }

        @Bean
        public OrderItemService orderItemService(OrderItemJpaRepository orderItemJpaRepository) {
            return new OrderItemServiceImp(orderItemJpaRepository);
        }

        @Bean
        public OrderService orderService(OrderJpaRepository orderJpaRepository,
                                         UserJpaRepository userJpaRepository,
                                         RestaurantJpaRepository restaurantJpaRepository,
                                         DelivererJpaRepository delivererJpaRepository,
                                         MenuItemJpaRepository menuItemJpaRepository,
                                         OrderItemJpaRepository orderItemJpaRepository,
                                         DiscountCodeJpaRepository discountCodeJpaRepository,
                                         OrderItemService orderItemService) {
            return new OrderServiceImp(orderJpaRepository, userJpaRepository, restaurantJpaRepository, delivererJpaRepository,
                    menuItemJpaRepository, discountCodeJpaRepository, orderItemJpaRepository, orderItemService);
        }

        @Bean
        public DelivererService delivererService(DelivererJpaRepository delivererJpaRepository, OrderJpaRepository orderJpaRepository) {
            return new DelivererServiceImp(delivererJpaRepository, orderJpaRepository);
        }

        @Bean
        public UserService userService(UserJpaRepository userJpaRepository) {
            return new UserServiceImp(userJpaRepository);
        }

        @Bean
        public OrderController orderController(OrderService orderService,
                                               DelivererService delivererService,
                                               UserService userService,
                                               ApplicationEventPublisher applicationEventPublisher) {
            return new OrderController(orderService, delivererService, userService, applicationEventPublisher);
        }
    }

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private OperationEvidenceJpaRepository operationEvidenceJpaRepository;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private DelivererJpaRepository delivererJpaRepository;

    @Autowired
    private IngredientJpaRepository ingredientJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private DishJpaRepository dishJpaRepository;

    @Autowired
    private MenuItemJpaRepository menuItemJpaRepository;

    @Autowired
    private OrderItemJpaRepository orderItemJpaRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderController orderController;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    private static final String STR_UUID = "e2ded76c-35a2-4bb2-8ef6-82cb2599b1de";
    @Autowired
    private DeliveryAddressJpaRepository deliveryAddressJpaRepository;

    @Test
    @Transactional
    public void put1() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1", "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);


        OrderDto orderJson = TestUtils.orderDto(STR_UUID, null, "Poproszę bez panierki", ConverterUtils.convert(user), ConverterUtils.convert(deliverer),
                ConverterUtils.convert(deliveryAddress), ConverterUtils.convert(restaurant),
                ConverterUtils.convert(TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1)));
        orderController.put(UUID.fromString(STR_UUID), orderJson);

        orderJson.setNetPrice(menuItem1.getNetPrice().multiply(new BigDecimal(orderJson.getOrderItemDtos().get(0).getQuantity())));
        orderJson.setGrossPrice(menuItem1.getGrossPrice().multiply(new BigDecimal(orderJson.getOrderItemDtos().get(0).getQuantity())));
        orderJson.setAmountToPayGross(menuItem1.getGrossPrice().multiply(new BigDecimal(orderJson.getOrderItemDtos().get(0).getQuantity())));

        OrderDto orderDB = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(orderJson, orderDB, AssertionUtils.OrderStatusType.NEW);

    }

    @Test
    @Transactional
    public void put2() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer1 = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer1);
        Deliverer deliverer2 = TestUtils.deliverer("9f15cfc3-2568-4fe3-8f94-034a23c9df6f",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith3", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer2);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        MenuItem menuItem2 = TestUtils.menuItem("44b2cd38-69eb-4178-b271-0bdef113eeea", "Powiększenie zestawu", new BigDecimal("8.00"),
                VatTax._23, new BigDecimal("9.84"), restaurant, dish1);
        menuItemJpaRepository.save(menuItem2);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer1, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderJpaRepository.save(order);

        OrderDto orderJson = TestUtils.orderDto(STR_UUID, null, "", ConverterUtils.convert(user),
                ConverterUtils.convert(deliverer2), ConverterUtils.convert(deliveryAddress), ConverterUtils.convert(restaurant),
                ConverterUtils.convert(orderItem),
                ConverterUtils.convert(TestUtils.orderItem("a9058ad1-5b4b-4cd1-aec2-fb3bc2b501b7", 2, menuItem2))
        );
        orderController.put(UUID.fromString(STR_UUID), orderJson);

        orderJson.setNetPrice(
                menuItem1.
                        getNetPrice().multiply(new BigDecimal(orderJson.getOrderItemDtos().get(0).getQuantity()))
                        .add(menuItem2
                                .getNetPrice().multiply(new BigDecimal(orderJson.getOrderItemDtos().get(1).getQuantity())))
        );
        orderJson.setGrossPrice(menuItem1.
                getGrossPrice().multiply(new BigDecimal(orderJson.getOrderItemDtos().get(0).getQuantity()))
                .add(menuItem2
                        .getGrossPrice().multiply(new BigDecimal(orderJson.getOrderItemDtos().get(1).getQuantity())))
        );
        orderJson.setAmountToPayGross(menuItem1.
                getGrossPrice().multiply(new BigDecimal(orderJson.getOrderItemDtos().get(0).getQuantity()))
                .add(menuItem2
                        .getGrossPrice().multiply(new BigDecimal(orderJson.getOrderItemDtos().get(1).getQuantity())))
        );

        OrderDto orderDB = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(orderJson, orderDB, AssertionUtils.OrderStatusType.NEW);
    }

    @Test
    @Transactional
    public void setIsPaid1() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceJpaRepository.save(operationEvidence);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderJpaRepository.save(order);

        orderController.patchIsPaid(UUID.fromString(STR_UUID));

        OrderDto orderDb = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        assert orderDb.getOrderStatusDto().getIsPaid();
    }

    // error while not enough founds
    @Test
    @Transactional
    public void setIsPaid2() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("10.00"), user);
        operationEvidenceJpaRepository.save(operationEvidence);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderJpaRepository.save(order);

        Assertions.assertThrows(ResponseStatusException.class, () -> orderController.patchIsPaid(UUID.fromString(STR_UUID)));
    }

    // giving out
    @Test
    @Transactional
    public void setIsGivedOut1() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceJpaRepository.save(operationEvidence);
        OperationEvidence operationEvidence2 = TestUtils.operationEvidence("2020-01-01T15:00:15Z", EvidenceType.PAYMENT, new BigDecimal("49.20"), user);
        operationEvidenceJpaRepository.save(operationEvidence2);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", true, null, null),
                orderItem
        );
        orderJpaRepository.save(order);

        OrderStatusDto orderStatusJson =
                TestUtils.orderStatusDto("2020-01-01T15:00:00Z", true, "2020-01-01T15:30:00Z", null);
        orderController.patchIsGiveOut(UUID.fromString(STR_UUID), orderStatusJson);

        OrderDto orderDb = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        Assertions.assertEquals(orderDb.getOrderStatusDto().getGiveOutTime(), orderStatusJson.getGiveOutTime());
    }

    // error when setting ordes as gived out when is not paid
    @Test
    @Transactional
    public void setIsGivedOut2() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceJpaRepository.save(operationEvidence);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderJpaRepository.save(order);

        OrderStatusDto orderStatusJson =
                TestUtils.orderStatusDto("2020-01-01T15:00:00Z", true, "2020-01-01T15:30:00Z", null);
        Assertions.assertThrows(ResponseStatusException.class, () -> orderController.patchIsGiveOut(UUID.fromString(STR_UUID), orderStatusJson));
    }

    // delivered
    @Test
    @Transactional
    public void setIsDelivered1() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceJpaRepository.save(operationEvidence);
        OperationEvidence operationEvidence2 = TestUtils.operationEvidence("2020-01-01T15:00:15Z", EvidenceType.PAYMENT, new BigDecimal("49.20"), user);
        operationEvidenceJpaRepository.save(operationEvidence2);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", true, "2020-01-01T16:00:00Z", null),
                orderItem
        );
        orderJpaRepository.save(order);

        OrderStatusDto orderStatusJson =
                TestUtils.orderStatusDto("2020-01-01T15:00:00Z", true, "2020-01-01T16:00:00Z", "2020-01-01T16:30:00Z");
        orderController.patchIsDelivered(UUID.fromString(STR_UUID), orderStatusJson);

        OrderDto orderDb = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        Assertions.assertEquals(orderDb.getOrderStatusDto().getDeliveryTime(), orderStatusJson.getDeliveryTime());
    }

    // error when setting order as delivered when is not paid
    @Test
    @Transactional
    public void setIsDelivered2() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceJpaRepository.save(operationEvidence);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderJpaRepository.save(order);

        OrderStatusDto orderStatusJson =
                TestUtils.orderStatusDto("2020-01-01T15:00:00Z", true, "2020-01-01T16:00:00Z", "2020-01-01T16:30:00Z");
        Assertions.assertThrows(ResponseStatusException.class, () -> orderController.patchIsDelivered(UUID.fromString(STR_UUID), orderStatusJson));
    }

    // error when setting order as delivered when is not gived out
    @Test
    @Transactional
    public void setIsDelivered3() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceJpaRepository.save(operationEvidence);
        OperationEvidence operationEvidence2 = TestUtils.operationEvidence("2020-01-01T15:00:15Z", EvidenceType.PAYMENT, new BigDecimal("49.20"), user);
        operationEvidenceJpaRepository.save(operationEvidence2);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", true, null, null),
                orderItem
        );
        orderJpaRepository.save(order);

        OrderStatusDto orderStatusJson =
                TestUtils.orderStatusDto("2020-01-01T15:00:00Z", true, "2020-01-01T16:00:00Z", "2020-01-01T16:30:00Z");
        Assertions.assertThrows(ResponseStatusException.class, () -> orderController.patchIsDelivered(UUID.fromString(STR_UUID), orderStatusJson));
    }

    // delete not paid order
    @Test
    @Transactional
    public void delete1() {
        TransactionStatus status1 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer1 = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer1);
        Deliverer deliverer2 = TestUtils.deliverer("9f15cfc3-2568-4fe3-8f94-034a23c9df6f",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith3", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer2);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        MenuItem menuItem2 = TestUtils.menuItem("44b2cd38-69eb-4178-b271-0bdef113eeea", "Powiększenie zestawu", new BigDecimal("8.00"),
                VatTax._23, new BigDecimal("9.84"), restaurant, dish1);
        menuItemJpaRepository.save(menuItem2);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer1, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderJpaRepository.save(order);
        platformTransactionManager.commit(status1);

        TransactionStatus status2 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        orderController.delete(UUID.fromString(STR_UUID));
        platformTransactionManager.commit(status2);

        TransactionStatus status3 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Truth8.assertThat(orderService.getByUuid(UUID.fromString(STR_UUID))).isEmpty();
        platformTransactionManager.commit(status3);
    }

    // error when delete paid order
    @Test
    @Transactional
    public void delete2() {
        TransactionStatus status1 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.logginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantJpaRepository.save(restaurant);

        Deliverer deliverer1 = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer1);
        Deliverer deliverer2 = TestUtils.deliverer("9f15cfc3-2568-4fe3-8f94-034a23c9df6f",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith3", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer2);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientJpaRepository.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientJpaRepository.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientJpaRepository.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientJpaRepository.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientJpaRepository.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientJpaRepository.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientJpaRepository.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productJpaRepository.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productJpaRepository.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productJpaRepository.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishJpaRepository.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishJpaRepository.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishJpaRepository.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemJpaRepository.save(menuItem1);

        MenuItem menuItem2 = TestUtils.menuItem("44b2cd38-69eb-4178-b271-0bdef113eeea", "Powiększenie zestawu", new BigDecimal("8.00"),
                VatTax._23, new BigDecimal("9.84"), restaurant, dish1);
        menuItemJpaRepository.save(menuItem2);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemJpaRepository.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer1, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", true, null, null),
                orderItem
        );
        orderJpaRepository.save(order);
        platformTransactionManager.commit(status1);

        TransactionStatus status2 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Assertions.assertThrows(ResponseStatusException.class, () -> orderController.delete(UUID.fromString(STR_UUID)));
        platformTransactionManager.commit(status2);
    }
}