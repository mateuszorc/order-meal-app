package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.config.JPAConfiguration;
import com.javaproject.eLaunchApp.listeners.OperationEvidenceListener;
import com.javaproject.eLaunchApp.repository.*;
import com.javaproject.eLaunchApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {
        JPAConfiguration.class,
        OrderControllerTest.Config.class
})
class OrderControllerTest {
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
        public OrderItemService orderItemService(OrderItemRepo orderItemRepo) {
            return new OrderItemServiceImpl(orderItemRepo);
        }
        @Bean
        public OrderService orderService(OrderRepo orderRepo,
                                         UserRepo userRepo,
                                         RestaurantRepo restaurantRepo,
                                         DelivererRepo delivererRepo,
                                         DeliveryAddressRepo deliveryAddressRepo,
                                         MenuItemRepo menuItemRepo,
                                         OrderItemRepo orderItemRepo,
                                         DiscountCodeRepo discountCodeRepo,
                                         OrderItemService orderItemService) {
            return new OrderServiceImpl(orderRepo, userRepo, restaurantRepo, delivererRepo, deliveryAddressRepo,
                    menuItemRepo, discountCodeRepo, orderItemRepo, orderItemService);
        }
        @Bean
        public UserService userService(UserRepo userRepo) {
            return new UserServiceImpl(userRepo);
        }
        @Bean
        public DelivererService delivererService(DelivererRepo delivererRepo, OrderRepo orderRepo) {
            return new DelivererServiceImpl(delivererRepo, orderRepo);
        }
        @Bean
        public OrderController orderController(OrderService orderService, DelivererService delivererService,
                                               UserService userService, ApplicationEventPublisher applicationEventPublisher) {
            return new OrderController(orderService, delivererService, userService, applicationEventPublisher);
        }
    }

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OperationEvidenceRepo operationEvidenceRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private DelivererRepo delivererRepo;

    @Autowired
    private DeliveryAddressRepo deliveryAddressRepo;

    @Autowired
    private IngredientRepo ingredientRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private DishRepo dishRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderController orderController;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static final String STR_UUID = "b229f150-eae0-4699-85f4-39c893cb0e5c";
}