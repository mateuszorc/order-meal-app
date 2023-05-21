package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.DTO.DeliveryAddressDTO;
import com.javaproject.eLaunchApp.DTO.DiscountCodeDTO;
import com.javaproject.eLaunchApp.config.JPAConfiguration;
import com.javaproject.eLaunchApp.models.DeliveryAddress;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.User;
import com.javaproject.eLaunchApp.models.enums.Archive;
import com.javaproject.eLaunchApp.models.enums.DiscountUnit;
import com.javaproject.eLaunchApp.models.enums.Sex;
import com.javaproject.eLaunchApp.repository.*;
import com.javaproject.eLaunchApp.service.*;
import com.javaproject.eLaunchApp.utils.AssertionUtils;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import com.javaproject.eLaunchApp.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {
        JPAConfiguration.class,
        DiscountCodeControllerTest.Config.class
})
class DiscountCodeControllerTest {
    @Configuration
    public static class Config {
        @Bean
        public DiscountCodeService discountCodeService(DiscountCodeRepo discountCodeRepo, UserRepo userRepo,
                                                       RestaurantRepo restaurantRepo) {
            return new DiscountCodeServiceImpl(discountCodeRepo, userRepo, restaurantRepo);
        }
        @Bean
        public DiscountCodeController discountCodeController(DiscountCodeService discountCodeService) {
            return new DiscountCodeController(discountCodeService);
        }
    }

    @Autowired
    private DiscountCodeRepo discountCodeRepo;

    @Autowired
    private DiscountCodeService discountCodeService;

    @Autowired
    private DiscountCodeController discountCodeController;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static final String STR_UUID = "67b759a5-4e82-4cb6-a704-51734e099f63";

    //add
    @Test
    @Transactional
    public void put1() {
        DiscountCodeDTO discountCodeJson = TestUtils.discountCodeDTO(STR_UUID, "BLACK FRIDAY", new BigDecimal("25.00"), DiscountUnit.PERCENT,
                "2023-05-20T00:00:00", "2024-05-20T00:00:00", null, null);
        discountCodeController.put(UUID.fromString(STR_UUID), discountCodeJson);

        DiscountCodeDTO discountCodeDB = discountCodeService.getByUuid(UUID.fromString(STR_UUID))
                .orElseThrow();
        AssertionUtils.assertEquals(discountCodeJson, discountCodeDB);
    }

    //update
    @Test
    @Transactional
    public void put2() {
        DiscountCode discountCode = TestUtils.discountCode(STR_UUID, "BLACK FRIDAY", new BigDecimal("25.00"), DiscountUnit.PERCENT,
                "2023-05-20T00:00:00", "2024-05-20T00:00:00", null, null);
        discountCodeRepo.save(discountCode);

        DiscountCodeDTO discountCodeJson = TestUtils.discountCodeDTO(STR_UUID, "BLACK FRIDAY1", new BigDecimal("20.00"), DiscountUnit.PLN,
                "2023-06-20T00:00:00", "2024-06-20T00:00:00", null, null);
        discountCodeController.put(UUID.fromString(STR_UUID), discountCodeJson);

        DiscountCodeDTO discountCodeDB = discountCodeService.getByUuid(UUID.fromString(STR_UUID))
                .orElseThrow();
        AssertionUtils.assertEquals(discountCodeJson, discountCodeDB);
    }
}