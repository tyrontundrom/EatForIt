package com.tyrontundrom.eatforit.controller;

import com.google.common.truth.Truth8;
import com.tyrontundrom.eatforit.config.JPAConfiguration;
import com.tyrontundrom.eatforit.dto.DiscountCodeDto;
import com.tyrontundrom.eatforit.model.DiscountCode;
import com.tyrontundrom.eatforit.model.enums.DiscountUnit;
import com.tyrontundrom.eatforit.repo.DiscountCodeJpaRepository;
import com.tyrontundrom.eatforit.repo.MenuItemJpaRepository;
import com.tyrontundrom.eatforit.repo.RestaurantJpaRepository;
import com.tyrontundrom.eatforit.repo.UserJpaRepository;
import com.tyrontundrom.eatforit.service.DiscountCodeService;
import com.tyrontundrom.eatforit.service.DiscountCodeServiceImp;
import com.tyrontundrom.eatforit.utils.AssertionUtils;
import com.tyrontundrom.eatforit.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(classes = {
        JPAConfiguration.class,
        DiscountCodeControllerTest.Config.class
})
class DiscountCodeControllerTest {

    @Configuration
    public static class Config {
        @Bean
        public DiscountCodeService discountCodeService(DiscountCodeJpaRepository discountCodeJpaRepository,
                                                       UserJpaRepository userJpaRepository,
                                                       RestaurantJpaRepository restaurantJpaRepository) {
            return new DiscountCodeServiceImp(discountCodeJpaRepository, userJpaRepository, restaurantJpaRepository);
        }

        @Bean
        public DiscountCodeController discountCodeController(DiscountCodeService discountCodeService) {
            return new DiscountCodeController(discountCodeService);
        }
    }

    @Autowired
    private DiscountCodeJpaRepository discountCodeJpaRepository;

    @Autowired
    private DiscountCodeService discountCodeService;

    @Autowired
    private DiscountCodeController discountCodeController;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static final String STR_UUID =  "527c9b15-9f9b-41b4-8d58-350d3e089b4e";
    @Autowired
    private MenuItemJpaRepository menuItemJpaRepository;

    @Test
    @Transactional
    public void put1() {
        DiscountCodeDto discountCodeJson = TestUtils.discountCodeDto(STR_UUID, "BLACK FRIDAY", new BigDecimal("25"), DiscountUnit.PERCENT,
                "2023-01-01T00:00:00", "2023-01-31T00:00:00", null, null);
        discountCodeController.put(UUID.fromString(STR_UUID), discountCodeJson);

        DiscountCodeDto discountCodeDb = discountCodeService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(discountCodeJson, discountCodeDb);
    }

    @Test
    @Transactional
    public void put2() {
        DiscountCode discountCode = TestUtils.discountCode(STR_UUID, "BLACK FRIDAY", new BigDecimal("25"), DiscountUnit.PERCENT,
                "2023-01-01T00:00:00", "2023-01-31T00:00:00", null, null);
        discountCodeJpaRepository.save(discountCode);

        DiscountCodeDto discountCodeJson = TestUtils.discountCodeDto(STR_UUID, "BLACK FRIDAY1", new BigDecimal("20"),
                DiscountUnit.PLN, "2023-07-01T00:00:00", "2023-04-31T00:00:00", null, null);
        discountCodeController.put(UUID.fromString(STR_UUID), discountCodeJson);

        DiscountCodeDto discountCodeDb = discountCodeService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(discountCodeJson, discountCodeDb);
    }

    @Test
    @Transactional
    public void delete() {
        TransactionStatus status1 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        DiscountCode discountCodeJson = TestUtils.discountCode(STR_UUID, "BLACK FRIDAY", new BigDecimal("25.00"), DiscountUnit.PERCENT,
                "2020-01-01T00:00:00", "2020-02-01T00:00:00", null, null);
        discountCodeJpaRepository.save(discountCodeJson);
        platformTransactionManager.commit(status1);

        TransactionStatus status2 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        discountCodeController.delete(UUID.fromString(STR_UUID));
        platformTransactionManager.commit(status2);

        TransactionStatus status3 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Truth8.assertThat(discountCodeService.getByUuid(UUID.fromString(STR_UUID))).isEmpty();
        platformTransactionManager.commit(status3);
    }
}