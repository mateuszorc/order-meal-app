package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.config.JPAConfiguration;
import com.javaproject.eLaunchApp.repository.*;
import com.javaproject.eLaunchApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

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
}