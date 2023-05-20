package com.javaproject.eLaunchApp.listeners;

import com.javaproject.eLaunchApp.config.JPAConfiguration;
import com.javaproject.eLaunchApp.controller.UserController;
import com.javaproject.eLaunchApp.repository.OperationEvidenceRepo;
import com.javaproject.eLaunchApp.repository.UserRepo;
import com.javaproject.eLaunchApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}