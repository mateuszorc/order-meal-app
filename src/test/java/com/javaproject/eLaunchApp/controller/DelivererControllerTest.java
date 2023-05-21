package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.config.JPAConfiguration;
import com.javaproject.eLaunchApp.models.Deliverer;
import com.javaproject.eLaunchApp.models.enums.Archive;
import com.javaproject.eLaunchApp.models.enums.Sex;
import com.javaproject.eLaunchApp.repository.DelivererRepo;
import com.javaproject.eLaunchApp.repository.OrderRepo;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.DelivererServiceImpl;
import com.javaproject.eLaunchApp.utils.AssertionUtils;
import com.javaproject.eLaunchApp.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        JPAConfiguration.class,
        DelivererControllerTest.Config.class
})
class DelivererControllerTest {
    @Configuration
    public static class Config {
        @Bean
        public DelivererService delivererService(DelivererRepo delivererRepo, OrderRepo orderRepo) {
            return new DelivererServiceImpl(delivererRepo, orderRepo);
        }
        @Bean
        public DelivererController delivererController(DelivererService delivererService) {
            return new DelivererController(delivererService);
        }
    }

    @Autowired
    private DelivererRepo delivererRepo;

    @Autowired
    private DelivererService delivererService;

    @Autowired
    private DelivererController delivererController;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static final String STR_UUID = "084a7439-0f76-4f27-9dcf-a6775fde216c";

    @Test
    @Transactional
    public void put1() {
        DelivererDTO delivererJson = TestUtils.delivererDTO(STR_UUID,
                TestUtils.personalDataDTO("John", "Smith", Sex.MALE, "888-777-999", "john123@gmail.com"),
                TestUtils.loginDataDTO("johnsmith", "dsanmbu#!@$Fasf"), Archive.CURRENT);
        delivererController.put(UUID.fromString(STR_UUID), delivererJson);

        DelivererDTO delivererDB = delivererService.getByUuid(UUID.fromString(STR_UUID))
                .orElseThrow();
        AssertionUtils.assertEquals(delivererJson, delivererDB);
    }

    @Test
    @Transactional
    public void put2() {
        Deliverer deliverer = TestUtils.deliverer(STR_UUID,
                TestUtils.personalData("John", "Smith", Sex.MALE, "888-777-999", "john123@gmail.com"),
                TestUtils.loginData("johnsmith", "dsanmbu#!@$Fasf"), Archive.CURRENT);
        delivererRepo.save(deliverer);

        DelivererDTO delivererJson = TestUtils.delivererDTO(STR_UUID,
                TestUtils.personalDataDTO("John1", "Smith2", Sex.FEMALE, "111-222-333", "john123@gmail.com"),
                TestUtils.loginDataDTO("johnsmith", "dsanmbu#!@$Fasf"), Archive.ARCHIVE);
        delivererController.put(UUID.fromString(STR_UUID), delivererJson);

        DelivererDTO delivererDB = delivererService.getByUuid(UUID.fromString(STR_UUID))
                .orElseThrow();
        AssertionUtils.assertEquals(delivererJson, delivererDB);
    }
}