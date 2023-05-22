package com.javaproject.eLaunchApp.controller;

import com.google.common.truth.Truth8;
import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.DeliveryAddressDTO;
import com.javaproject.eLaunchApp.config.JPAConfiguration;
import com.javaproject.eLaunchApp.models.Deliverer;
import com.javaproject.eLaunchApp.models.DeliveryAddress;
import com.javaproject.eLaunchApp.models.User;
import com.javaproject.eLaunchApp.models.enums.Archive;
import com.javaproject.eLaunchApp.models.enums.Sex;
import com.javaproject.eLaunchApp.repository.DelivererRepo;
import com.javaproject.eLaunchApp.repository.DeliveryAddressRepo;
import com.javaproject.eLaunchApp.repository.OrderRepo;
import com.javaproject.eLaunchApp.repository.UserRepo;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.DelivererServiceImpl;
import com.javaproject.eLaunchApp.service.DeliveryAddressService;
import com.javaproject.eLaunchApp.service.DeliveryAddressServiceImpl;
import com.javaproject.eLaunchApp.utils.AssertionUtils;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import com.javaproject.eLaunchApp.utils.TestUtils;
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

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {
        JPAConfiguration.class,
        DeliveryAddressControllerTest.Config.class
})
class DeliveryAddressControllerTest {
    @Configuration
    public static class Config {
        @Bean
        public DeliveryAddressService deliveryAddressService(DeliveryAddressRepo deliveryAddressRepo, UserRepo userRepo) {
            return new DeliveryAddressServiceImpl(deliveryAddressRepo, userRepo);
        }
        @Bean
        public DeliveryAddressController deliveryAddressController(DeliveryAddressService deliveryAddressService) {
            return new DeliveryAddressController(deliveryAddressService);
        }
    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DeliveryAddressRepo deliveryAddressRepo;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private DeliveryAddressController deliveryAddressController;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static final String STR_UUID = "54eec33c-24cf-46c4-b124-cb658fc364cc";

    //add
    @Test
    @Transactional
    public void put1() {
        User user = TestUtils.user("d1e1ec67-c05a-4a4a-8d1e-6f22f21b2859",
                TestUtils.personalData("John", "Smith", Sex.MALE, "888-777-999", "john123@gmail.com")
                , null,
                TestUtils.loginData("johnsmith", "dsanmbu#!@$Fasf"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddressDTO deliveryAddresJson = TestUtils.deliveryAddressDTO(STR_UUID, "My address",
                "Street", "51", "null", "00-000", "Poznan", null,
                "Poland", null, ConvertUtils.convert(user));
        deliveryAddressController.put(UUID.fromString(STR_UUID), deliveryAddresJson);

        DeliveryAddressDTO deliveryAddressDB = deliveryAddressService.getByUuid(UUID.fromString(STR_UUID))
                .orElseThrow();
        AssertionUtils.assertEquals(deliveryAddresJson, deliveryAddressDB);
    }

    //update
    @Test
    @Transactional
    public void put2() {
        User user = TestUtils.user("d1e1ec67-c05a-4a4a-8d1e-6f22f21b2859",
                TestUtils.personalData("John", "Smith", Sex.MALE, "888-777-999", "john123@gmail.com")
                , null,
                TestUtils.loginData("johnsmith", "dsanmbu#!@$Fasf"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddres = TestUtils.deliveryAddress(STR_UUID, "My address",
                "Street", "51", "null", "00-000", "Poznan", null,
                "Poland", null, user);
        deliveryAddressRepo.save(deliveryAddres);

        DeliveryAddressDTO deliveryAddresJson = TestUtils.deliveryAddressDTO(STR_UUID, "My address1",
                "Street1", "11", "null", "11-000", "Poznań", "1",
                "Poland", null, ConvertUtils.convert(user));
        deliveryAddressController.put(UUID.fromString(STR_UUID), deliveryAddresJson);

        DeliveryAddressDTO deliveryAddressDB = deliveryAddressService.getByUuid(UUID.fromString(STR_UUID))
                .orElseThrow();
        AssertionUtils.assertEquals(deliveryAddresJson, deliveryAddressDB);
    }

    @Test
    @Transactional
    public void delete() {
        TransactionStatus transaction = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        User user = TestUtils.user("d1e1ec67-c05a-4a4a-8d1e-6f22f21b2859",
                TestUtils.personalData("John", "Smith", Sex.MALE, "888-777-999", "john123@gmail.com")
                , null,
                TestUtils.loginData("johnsmith", "dsanmbu#!@$Fasf"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddres = TestUtils.deliveryAddress(STR_UUID, "My address",
                "Street", "51", "null", "00-000", "Poznan", null,
                "Poland", null, user);
        deliveryAddressRepo.save(deliveryAddres);
        platformTransactionManager.commit(transaction);

        TransactionStatus transaction2 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        deliveryAddressController.delete(UUID.fromString(STR_UUID));
        platformTransactionManager.commit(transaction2);

        TransactionStatus transaction3 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Truth8.assertThat(deliveryAddressService.getByUuid(UUID.fromString(STR_UUID))).isEmpty();
        platformTransactionManager.commit(transaction3);
    }
}