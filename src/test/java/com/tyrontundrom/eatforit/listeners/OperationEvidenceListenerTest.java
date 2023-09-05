package com.tyrontundrom.eatforit.listeners;

import com.tyrontundrom.eatforit.config.JPAConfiguration;
import com.tyrontundrom.eatforit.controller.UserController;
import com.tyrontundrom.eatforit.dto.UserDto;
import com.tyrontundrom.eatforit.model.OperationEvidence;
import com.tyrontundrom.eatforit.model.User;
import com.tyrontundrom.eatforit.model.enums.Archive;
import com.tyrontundrom.eatforit.model.enums.EvidenceType;
import com.tyrontundrom.eatforit.model.enums.Sex;
import com.tyrontundrom.eatforit.repo.OperationEvidenceJpaRepository;
import com.tyrontundrom.eatforit.repo.UserJpaRepository;
import com.tyrontundrom.eatforit.service.OperationEvidenceService;
import com.tyrontundrom.eatforit.service.OperationEvidenceServiceImp;
import com.tyrontundrom.eatforit.service.UserService;
import com.tyrontundrom.eatforit.service.UserServiceImp;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import com.tyrontundrom.eatforit.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = {
        JPAConfiguration.class,
        OperationEvidenceListenerTest.Config.class
})
class OperationEvidenceListenerTest {

    @Configuration
    public static class Config {
        @Autowired
        private OperationEvidenceJpaRepository operationEvidenceJpaRepository;

        @Bean
        public OperationEvidenceService operationEvidenceService(OperationEvidenceJpaRepository operationEvidenceJpaRepository) {
            return new OperationEvidenceServiceImp(operationEvidenceJpaRepository);
        }

        @Bean
        public OperationEvidenceListener operationEvidenceListener(OperationEvidenceService operationEvidenceService,
                                                                   UserJpaRepository userJpaRepository) {
            return new OperationEvidenceListener(operationEvidenceService, userJpaRepository);
        }

        @Bean
        public UserService userService(UserJpaRepository userJpaRepository) {
            return new UserServiceImp(userJpaRepository);
        }

        @Bean
        public UserController userController(UserService userService, ApplicationEventPublisher applicationEventPublisher) {
            return new UserController(userService, applicationEventPublisher);
        }
    }

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private OperationEvidenceJpaRepository operationEvidenceJpaRepository;

    @Autowired
    private UserController userController;

    private static final String STR_UUID = "833e3557-7777-4954-8b9c-fed3769e510a";

    @Test
    @Transactional
    public void deposit() {
        User user = TestUtils.user(STR_UUID, TestUtils.personalData("John", "Smith",
                        Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        UserDto userJson = ConverterUtils.convert(user);
        userJson.setOperationEvidenceDtos(List.of(
                TestUtils.operationEvidenceDto("2020-01-01T12:00:00Z", EvidenceType.DEPOSIT, new BigDecimal("100.00"), userJson)
        ));
        userController.postOperation(UUID.fromString(STR_UUID), userJson);

        BigDecimal userAccountBalance = operationEvidenceJpaRepository.getUserAccountBalance(user);
        Assertions.assertEquals(new BigDecimal("100.00"), userAccountBalance);
    }

    @Test
    @Transactional
    public void withdraw() {
        User user = TestUtils.user(STR_UUID, TestUtils.personalData("John", "Smith",
                        Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);
        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T12:00:00Z", EvidenceType.DEPOSIT, new BigDecimal("100.00"), user);
        operationEvidenceJpaRepository.save(operationEvidence);

        UserDto userJson = ConverterUtils.convert(user);
        userJson.setOperationEvidenceDtos(List.of(
                TestUtils.operationEvidenceDto("2020-01-01T12:00:00Z", EvidenceType.WITHDRAW, new BigDecimal("25.00"), userJson)
        ));
        userController.postOperation(UUID.fromString(STR_UUID), userJson);

        BigDecimal userAccountBalance = operationEvidenceJpaRepository.getUserAccountBalance(user);
        Assertions.assertEquals(new BigDecimal("75.00"), userAccountBalance);
    }

    @Test
    @Transactional
    public void payment() {
        User user = TestUtils.user(STR_UUID, TestUtils.personalData("John", "Smith",
                        Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);
        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T12:00:00Z", EvidenceType.DEPOSIT, new BigDecimal("100.00"), user);
        operationEvidenceJpaRepository.save(operationEvidence);

        UserDto userJson = ConverterUtils.convert(user);
        userJson.setOperationEvidenceDtos(List.of(
                TestUtils.operationEvidenceDto("2020-01-01T12:00:00Z", EvidenceType.PAYMENT, new BigDecimal("25.00"), userJson)
        ));
        userController.postOperation(UUID.fromString(STR_UUID), userJson);

        BigDecimal userAccountBalance = operationEvidenceJpaRepository.getUserAccountBalance(user);
        Assertions.assertEquals(new BigDecimal("75.00"), userAccountBalance);
    }

    @Test
    @Transactional
    public void minusBalance() {
        User user = TestUtils.user(STR_UUID, TestUtils.personalData("John", "Smith",
                        Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.logginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userJpaRepository.save(user);

        UserDto userJson = ConverterUtils.convert(user);
        userJson.setOperationEvidenceDtos(List.of(
                TestUtils.operationEvidenceDto("2020-01-01T12:00:00Z", EvidenceType.WITHDRAW, new BigDecimal("100.00"), userJson)
        ));
        Assertions.assertThrows(ResponseStatusException.class, () -> userController.postOperation(UUID.fromString(STR_UUID), userJson));
    }
}
