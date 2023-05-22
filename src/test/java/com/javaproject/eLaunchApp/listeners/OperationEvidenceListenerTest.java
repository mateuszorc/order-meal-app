package com.javaproject.eLaunchApp.listeners;

import com.javaproject.eLaunchApp.DTO.UserDTO;
import com.javaproject.eLaunchApp.config.JPAConfiguration;
import com.javaproject.eLaunchApp.controller.UserController;
import com.javaproject.eLaunchApp.models.OperationEvidence;
import com.javaproject.eLaunchApp.models.User;
import com.javaproject.eLaunchApp.models.enums.Archive;
import com.javaproject.eLaunchApp.models.enums.EvidenceType;
import com.javaproject.eLaunchApp.models.enums.Sex;
import com.javaproject.eLaunchApp.repository.OperationEvidenceRepo;
import com.javaproject.eLaunchApp.repository.UserRepo;
import com.javaproject.eLaunchApp.service.*;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import com.javaproject.eLaunchApp.utils.TestUtils;
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

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {
        JPAConfiguration.class,
        OperationEvidenceListenerTest.Config.class
})
class OperationEvidenceListenerTest {
    @Configuration
    public static class Config {
        @Bean
        public OperationEvidenceService operationEvidenceService(OperationEvidenceRepo operationEvidenceRepo) {
            return new OperationEvidenceServiceImpl(operationEvidenceRepo);
        }
        @Bean
        public OperationEvidenceListener operationEvidenceListener(OperationEvidenceService operationEvidenceService,
                                                                   UserRepo userRepo) {
            return new OperationEvidenceListener(operationEvidenceService, userRepo);
        }
        @Bean
        public UserService userService(UserRepo userRepo) {
            return new UserServiceImpl(userRepo);
        }
        @Bean
        public UserController userController(UserService userService, ApplicationEventPublisher applicationEventPublisher) {
            return new UserController(userService, applicationEventPublisher);
        }
    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OperationEvidenceRepo operationEvidenceRepo;

    @Autowired
    private UserController userController;

    private static final String STR_UUID = "a5c0d04c-ab9e-41ac-9479-e9cda93662b7";

    @Test
    @Transactional
    public void deposit() {
        User user = TestUtils.user(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        UserDTO userJson = ConvertUtils.convert(user);
        userJson.setOperationEvidences(List.of(
                TestUtils.operationEvidenceDTO("2023-01-01T12:00:00Z", EvidenceType.DEPOSIT, new BigDecimal("100.00"), userJson)
        ));
        userController.postOperation(UUID.fromString(STR_UUID), userJson);

        BigDecimal userAccountBalance = operationEvidenceRepo.getUserAccountBalance(user);
        Assertions.assertEquals(new BigDecimal("100.00"), userAccountBalance);
    }

    @Test
    @Transactional
    public void withdraw() {
        User user = TestUtils.user(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);
        OperationEvidence operationEvidence = TestUtils.operationEvidence("2023-01-01T12:00:00Z", EvidenceType.DEPOSIT, new BigDecimal("100.00"), user);
        operationEvidenceRepo.save(operationEvidence);

        UserDTO userJson = ConvertUtils.convert(user);
        userJson.setOperationEvidences(List.of(
                TestUtils.operationEvidenceDTO("2023-01-01T12:00:00Z", EvidenceType.WITHDRAW, new BigDecimal("25.00"), userJson)
        ));
        userController.postOperation(UUID.fromString(STR_UUID), userJson);

        BigDecimal userAccountBalance = operationEvidenceRepo.getUserAccountBalance(user);
        Assertions.assertEquals(new BigDecimal("75.00"), userAccountBalance);
    }

    @Test
    @Transactional
    public void payment() {
        User user = TestUtils.user(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);
        OperationEvidence operationEvidence = TestUtils.operationEvidence("2023-01-01T12:00:00Z", EvidenceType.DEPOSIT, new BigDecimal("100.00"), user);
        operationEvidenceRepo.save(operationEvidence);

        UserDTO userJson = ConvertUtils.convert(user);
        userJson.setOperationEvidences(List.of(
                TestUtils.operationEvidenceDTO("2023-01-01T12:00:00Z", EvidenceType.PAYMENT, new BigDecimal("25.00"), userJson)
        ));
        userController.postOperation(UUID.fromString(STR_UUID), userJson);

        BigDecimal userAccountBalance = operationEvidenceRepo.getUserAccountBalance(user);
        Assertions.assertEquals(new BigDecimal("75.00"), userAccountBalance);
    }

    @Test
    @Transactional
    public void minusBalance() {
        User user = TestUtils.user(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        UserDTO userJson = ConvertUtils.convert(user);
        userJson.setOperationEvidences(List.of(
                TestUtils.operationEvidenceDTO("2023-01-01T12:00:00Z", EvidenceType.WITHDRAW, new BigDecimal("100.00"), userJson)
        ));
        Assertions.assertThrows(ResponseStatusException.class, () -> userController.postOperation(UUID.fromString(STR_UUID),userJson));
    }
}