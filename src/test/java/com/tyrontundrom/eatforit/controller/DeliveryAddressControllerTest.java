package com.tyrontundrom.eatforit.controller;

import com.google.common.truth.Truth8;
import com.tyrontundrom.eatforit.config.JPAConfiguration;
import com.tyrontundrom.eatforit.dto.DeliveryAddressDto;
import com.tyrontundrom.eatforit.model.DeliveryAddress;
import com.tyrontundrom.eatforit.model.User;
import com.tyrontundrom.eatforit.model.enums.Archive;
import com.tyrontundrom.eatforit.model.enums.Sex;
import com.tyrontundrom.eatforit.repo.DeliveryAddressJpaRepository;
import com.tyrontundrom.eatforit.repo.UserJpaRepository;
import com.tyrontundrom.eatforit.service.DeliveryAddressService;
import com.tyrontundrom.eatforit.service.DeliveryAddressServiceImp;
import com.tyrontundrom.eatforit.utils.AssertionUtils;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
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

import java.util.UUID;

@SpringBootTest(classes = {
        JPAConfiguration.class,
        DeliveryAddressControllerTest.Config.class
})
class DeliveryAddressControllerTest {

    @Configuration
    public static class Config {
        @Bean
        public DeliveryAddressService deliveryAddressService(DeliveryAddressJpaRepository deliveryAddressJpaRepository, UserJpaRepository userJpaRepository) {
            return new DeliveryAddressServiceImp(deliveryAddressJpaRepository, userJpaRepository);
        }

        @Bean
        public DeliveryAddressController deliveryAddressController(DeliveryAddressService deliveryAddressService) {
            return new DeliveryAddressController(deliveryAddressService);
        }
    }

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private DeliveryAddressJpaRepository deliveryAddressJpaRepository;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private DeliveryAddressController deliveryAddressController;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static final String STR_UUID =  "da7cde37-658e-4979-91cb-1a5455fada72";

    @Test
    @Transactional
    public void put1() {
        User user = TestUtils.user("88e71092-63f2-40a6-b66a-c1bd0565780c",
                TestUtils.personalData("John", "Walker", Sex.MALE, "606-786-009", "mail@domain.com"),
                null,
                TestUtils.logginData("walkie", "pass"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddressDto deliveryAddressJson = TestUtils.deliveryAddressDto(STR_UUID,"Address", "Street",
        "55", "33", "99-999", "City", null, "Country", null,
                ConverterUtils.convert(user));
        deliveryAddressController.put(UUID.fromString(STR_UUID), deliveryAddressJson);

        DeliveryAddressDto deliveryAddressDb = deliveryAddressService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(deliveryAddressJson, deliveryAddressDb);
    }

    @Test
    @Transactional
    public void put2() {
        User user = TestUtils.user("88e71092-63f2-40a6-b66a-c1bd0565780c",
                TestUtils.personalData("John", "Walker", Sex.MALE, "606-786-009", "mail@domain.com"),
                null,
                TestUtils.logginData("walkie", "pass"), Archive.CURRENT);
        userJpaRepository.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress(STR_UUID,"Address1", "Street1",
                "55", "33", "99-999", "City1", null, "Country1", null,
                user);
        deliveryAddressJpaRepository.save(deliveryAddress);

        DeliveryAddressDto deliveryAddressJson = TestUtils.deliveryAddressDto(STR_UUID,"Address", "Street",
                "66", "55", "22-222", "City", null, "Country", null,
                ConverterUtils.convert(user));
        deliveryAddressController.put(UUID.fromString(STR_UUID), deliveryAddressJson);

        DeliveryAddressDto deliveryAddressDb = deliveryAddressService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(deliveryAddressJson, deliveryAddressDb);
    }

    @Test
    @Transactional
    public void delete() {
        TransactionStatus status1 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        userJpaRepository.save(user);
        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress(STR_UUID, "My address", "Street",
                "51", "", "00-000", "Warsaw", null, "Polans", null, user);
        deliveryAddressJpaRepository.save(deliveryAddress);
        platformTransactionManager.commit(status1);

        TransactionStatus status2 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        deliveryAddressController.delete(UUID.fromString(STR_UUID));
        platformTransactionManager.commit(status2);

        TransactionStatus status3 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Truth8.assertThat(deliveryAddressService.getByUuid(UUID.fromString(STR_UUID))).isEmpty();
        platformTransactionManager.commit(status3);
    }
}