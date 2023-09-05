package com.tyrontundrom.eatforit.controller;

import com.google.common.truth.Truth8;
import com.tyrontundrom.eatforit.config.JPAConfiguration;
import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.model.Deliverer;
import com.tyrontundrom.eatforit.model.enums.Archive;
import com.tyrontundrom.eatforit.model.enums.Sex;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.DelivererServiceImp;
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

import java.util.UUID;

@SpringBootTest(classes = {
        JPAConfiguration.class,
        DelivererControllerTest.Config.class
})
class DelivererControllerTest {

    @Configuration
    public static class Config {
        @Bean
        public DelivererService delivererService(DelivererJpaRepository delivererJpaRepository, OrderJpaRepository orderJpaRepository) {
            return new DelivererServiceImp(delivererJpaRepository, orderJpaRepository);
        }

        @Bean
        public DelivererController delivererController(DelivererService delivererService) {
            return new DelivererController(delivererService);
        }
    }

    @Autowired
    private DelivererJpaRepository delivererJpaRepository;

    @Autowired
    private DelivererService delivererService;

    @Autowired
    private DelivererController delivererController;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static final String STR_UUID = "42b960b3-0610-4912-886d-c80cb4b3d0a5";

    @Test
    @Transactional
    public void put1() {
        DelivererDto delivererJson = TestUtils.delivererDto(STR_UUID,
                TestUtils.personalDataDto("John", "Walker", Sex.MALE, "606-786-009", "mail@domain.com"),
                TestUtils.logginDataDto("walkie", "pass"), Archive.CURRENT);
        delivererController.put(UUID.fromString(STR_UUID), delivererJson);

        DelivererDto delivererDb = delivererService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(delivererJson, delivererDb);
    }

    @Test
    @Transactional
    public void put2() {
        Deliverer deliverer = TestUtils.deliverer(STR_UUID,
                TestUtils.personalData("John", "Walker", Sex.MALE, "606-786-009", "mail@domain.com"),
                TestUtils.logginData("walkie", "pass"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);

        DelivererDto delivererJson = TestUtils.delivererDto(STR_UUID,
                TestUtils.personalDataDto("John1", "Walker1", Sex.FEMALE, "606-000-009", "mailer@domain.com"),
                TestUtils.logginDataDto("walkie1", "pass1"), Archive.CURRENT);
        delivererController.put(UUID.fromString(STR_UUID), delivererJson);

        DelivererDto delivererDb = delivererService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(delivererJson, delivererDb);
    }

    @Test
    @Transactional
    public void delete() {
        TransactionStatus status1 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Deliverer deliverer = TestUtils.deliverer(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.logginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererJpaRepository.save(deliverer);
        platformTransactionManager.commit(status1);

        TransactionStatus status2 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        delivererController.delete(UUID.fromString(STR_UUID));
        platformTransactionManager.commit(status2);

        TransactionStatus status3 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Truth8.assertThat(delivererService.getByUuid(UUID.fromString(STR_UUID))).isEmpty();
        platformTransactionManager.commit(status3);
    }
}