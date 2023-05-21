package com.javaproject.eLaunchApp.controller;

import com.google.common.truth.Truth8;
import com.javaproject.eLaunchApp.DTO.DiscountCodeDTO;
import com.javaproject.eLaunchApp.DTO.OrderDTO;
import com.javaproject.eLaunchApp.DTO.OrderStatusDTO;
import com.javaproject.eLaunchApp.config.JPAConfiguration;
import com.javaproject.eLaunchApp.listeners.OperationEvidenceListener;
import com.javaproject.eLaunchApp.models.*;
import com.javaproject.eLaunchApp.models.enums.*;
import com.javaproject.eLaunchApp.repository.*;
import com.javaproject.eLaunchApp.service.*;
import com.javaproject.eLaunchApp.utils.AssertionUtils;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import com.javaproject.eLaunchApp.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

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

    // add
    @Test
    @Transactional
    public void put1() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1", "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);


        OrderDTO orderJson = TestUtils.orderDTO(STR_UUID, null, "Poproszę bez panierki", ConvertUtils.convert(user), ConvertUtils.convert(deliverer),
                ConvertUtils.convert(deliveryAddress), ConvertUtils.convert(restaurant),
                ConvertUtils.convert(TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1)));
        orderController.put(UUID.fromString(STR_UUID), orderJson);

        orderJson.setNettoPrice(menuItem1.getNettoPrice().multiply(new BigDecimal(orderJson.getOrderItems().get(0).getQuantity())));
        orderJson.setBruttoPrice(menuItem1.getBruttoPrice().multiply(new BigDecimal(orderJson.getOrderItems().get(0).getQuantity())));
        orderJson.setAmountToPayBrutto(menuItem1.getBruttoPrice().multiply(new BigDecimal(orderJson.getOrderItems().get(0).getQuantity())));

        OrderDTO orderDB = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(orderJson, orderDB, AssertionUtils.OrderStatusType.NEW);
    }

    // update order in case is not paid
    @Test
    @Transactional
    public void put2() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer1 = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer1);
        Deliverer deliverer2 = TestUtils.deliverer("9f15cfc3-2568-4fe3-8f94-034a23c9df6f",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith3", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer2);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        MenuItem menuItem2 = TestUtils.menuItem("44b2cd38-69eb-4178-b271-0bdef113eeea", "Powiększenie zestawu", new BigDecimal("8.00"),
                VatTax._23, new BigDecimal("9.84"), restaurant, dish1);
        menuItemRepo.save(menuItem2);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer1, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderRepo.save(order);

        OrderDTO orderJson = TestUtils.orderDTO(STR_UUID, null, "", ConvertUtils.convert(user),
                ConvertUtils.convert(deliverer2), ConvertUtils.convert(deliveryAddress), ConvertUtils.convert(restaurant),
                ConvertUtils.convert(orderItem),
                ConvertUtils.convert(TestUtils.orderItem("a9058ad1-5b4b-4cd1-aec2-fb3bc2b501b7", 2, menuItem2))
        );
        orderController.put(UUID.fromString(STR_UUID), orderJson);

        orderJson.setNettoPrice(
                menuItem1.
                        getNettoPrice().multiply(new BigDecimal(orderJson.getOrderItems().get(0).getQuantity()))
                        .add(menuItem2
                                .getNettoPrice().multiply(new BigDecimal(orderJson.getOrderItems().get(1).getQuantity())))
        );
        orderJson.setBruttoPrice(menuItem1.
                getBruttoPrice().multiply(new BigDecimal(orderJson.getOrderItems().get(0).getQuantity()))
                .add(menuItem2
                        .getBruttoPrice().multiply(new BigDecimal(orderJson.getOrderItems().get(1).getQuantity())))
        );
        orderJson.setAmountToPayBrutto(menuItem1.
                getBruttoPrice().multiply(new BigDecimal(orderJson.getOrderItems().get(0).getQuantity()))
                .add(menuItem2
                        .getBruttoPrice().multiply(new BigDecimal(orderJson.getOrderItems().get(1).getQuantity())))
        );

        OrderDTO orderDB = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        AssertionUtils.assertEquals(orderJson, orderDB, AssertionUtils.OrderStatusType.NEW);
    }

    // paying for order
    @Test
    @Transactional
    public void setIsPaid1() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceRepo.save(operationEvidence);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderRepo.save(order);

        orderController.patchIsPaid(UUID.fromString(STR_UUID));

        OrderDTO orderDB = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        assert orderDB.getOrderStatusDTO().getPaid();
    }

    // error while not enough founds
    @Test
    @Transactional
    public void setIsPaid2() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("10.00"), user);
        operationEvidenceRepo.save(operationEvidence);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderRepo.save(order);

        Assertions.assertThrows(ResponseStatusException.class, () -> orderController.patchIsPaid(UUID.fromString(STR_UUID)));
    }

    // giving out
    @Test
    @Transactional
    public void setIsGivedOut1() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceRepo.save(operationEvidence);
        OperationEvidence operationEvidence2 = TestUtils.operationEvidence("2020-01-01T15:00:15Z", EvidenceType.PAYMENT, new BigDecimal("49.20"), user);
        operationEvidenceRepo.save(operationEvidence2);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", true, null, null),
                orderItem
        );
        orderRepo.save(order);

        OrderStatusDTO orderStatusJson =
                TestUtils.orderStatusDTO("2020-01-01T15:00:00Z", true, "2020-01-01T15:30:00Z", null);
        orderController.patchIsGivenOut(UUID.fromString(STR_UUID), orderStatusJson);

        OrderDTO orderDB = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        Assertions.assertEquals(orderDB.getOrderStatusDTO().getGiveOutTime(), orderStatusJson.getGiveOutTime());
    }

    // error when setting ordes as gived out when is not paid
    @Test
    @Transactional
    public void setIsGivedOut2() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceRepo.save(operationEvidence);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderRepo.save(order);

        OrderStatusDTO orderStatusJson =
                TestUtils.orderStatusDTO("2020-01-01T15:00:00Z", true, "2020-01-01T15:30:00Z", null);
        assertThrows(ResponseStatusException.class, () -> orderController.patchIsGivenOut(UUID.fromString(STR_UUID), orderStatusJson));
    }

    // delivered
    @Test
    @Transactional
    public void setIsDelivered1() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceRepo.save(operationEvidence);
        OperationEvidence operationEvidence2 = TestUtils.operationEvidence("2020-01-01T15:00:15Z", EvidenceType.PAYMENT, new BigDecimal("49.20"), user);
        operationEvidenceRepo.save(operationEvidence2);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", true, "2020-01-01T16:00:00Z", null),
                orderItem
        );
        orderRepo.save(order);

        OrderStatusDTO orderStatusJson =
                TestUtils.orderStatusDTO("2020-01-01T15:00:00Z", true, "2020-01-01T16:00:00Z", "2020-01-01T16:30:00Z");
        orderController.patchIsPDelivered(UUID.fromString(STR_UUID), orderStatusJson);

        OrderDTO orderDB = orderService.getByUuid(UUID.fromString(STR_UUID)).orElseThrow();
        assertEquals(orderDB.getOrderStatusDTO().getDeliveryTime(), orderStatusJson.getDeliveryTime());
    }

    // error when setting order as delivered when is not paid
    @Test
    @Transactional
    public void setIsDelivered2() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceRepo.save(operationEvidence);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderRepo.save(order);

        OrderStatusDTO orderStatusJson =
                TestUtils.orderStatusDTO("2020-01-01T15:00:00Z", true, "2020-01-01T16:00:00Z", "2020-01-01T16:30:00Z");
        assertThrows(ResponseStatusException.class, () -> orderController.patchIsPDelivered(UUID.fromString(STR_UUID), orderStatusJson));
    }

    // error when setting order as delivered when is not gived out
    @Test
    @Transactional
    public void setIsDelivered3() {
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        OperationEvidence operationEvidence = TestUtils.operationEvidence("2020-01-01T14:00:15Z", EvidenceType.DEPOSIT, new BigDecimal("50.00"), user);
        operationEvidenceRepo.save(operationEvidence);
        OperationEvidence operationEvidence2 = TestUtils.operationEvidence("2020-01-01T15:00:15Z", EvidenceType.PAYMENT, new BigDecimal("49.20"), user);
        operationEvidenceRepo.save(operationEvidence2);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", true, null, null),
                orderItem
        );
        orderRepo.save(order);

        OrderStatusDTO orderStatusJson =
                TestUtils.orderStatusDTO("2020-01-01T15:00:00Z", true, "2020-01-01T16:00:00Z", "2020-01-01T16:30:00Z");
        assertThrows(ResponseStatusException.class, () -> orderController.patchIsPDelivered(UUID.fromString(STR_UUID), orderStatusJson));
    }

    // delete not paid order
    @Test
    @Transactional
    public void delete1() {
        TransactionStatus status1 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer1 = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer1);
        Deliverer deliverer2 = TestUtils.deliverer("9f15cfc3-2568-4fe3-8f94-034a23c9df6f",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith3", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer2);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        MenuItem menuItem2 = TestUtils.menuItem("44b2cd38-69eb-4178-b271-0bdef113eeea", "Powiększenie zestawu", new BigDecimal("8.00"),
                VatTax._23, new BigDecimal("9.84"), restaurant, dish1);
        menuItemRepo.save(menuItem2);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer1, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", false, null, null),
                orderItem
        );
        orderRepo.save(order);
        platformTransactionManager.commit(status1);

        TransactionStatus status2 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        orderController.delete(UUID.fromString(STR_UUID));
        platformTransactionManager.commit(status2);

        TransactionStatus status3 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Truth8.assertThat(orderService.getByUuid(UUID.fromString(STR_UUID))).isEmpty();
        platformTransactionManager.commit(status3);
    }

    // error when delete paid order
    @Test
    @Transactional
    public void delete2() {
        TransactionStatus status1 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        Restaurant restaurant = TestUtils.restaurant("087c502c-559b-4d81-ae53-e67ab6fdf995", "MakJson",
                TestUtils.loginData("mcJson123", "bigDag123!"),
                TestUtils.companyData("MakJson sp. z zoo",
                        TestUtils.address("St. Patric", "152", "00-000", "NY"),
                        "123-010-00-11", "123123123", "+48 501 502 503", "MJ@gmail.com"), Archive.CURRENT);
        restaurantRepo.save(restaurant);

        Deliverer deliverer1 = TestUtils.deliverer("cb093f68-f4f1-4f21-a094-5850150b42dd",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer1);
        Deliverer deliverer2 = TestUtils.deliverer("9f15cfc3-2568-4fe3-8f94-034a23c9df6f",
                TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"),
                TestUtils.loginData("jSmith3", "I@mIronM@n12"), Archive.CURRENT);
        delivererRepo.save(deliverer2);

        User user = TestUtils.user("9986208e-961a-48d4-bf7a-112c627779c2", TestUtils.personalData("John", "Smith", Sex.MALE, "501-501-501", "jh512@gmail.com"), null,
                TestUtils.loginData("jSmith1", "I@mIronM@n121"), Archive.CURRENT);
        userRepo.save(user);

        DeliveryAddress deliveryAddress = TestUtils.deliveryAddress("6d66a218-f493-41ee-8291-a0065fab5cfe", null, "Jana Pawła", "1",
                "", "00-010", "Warszawa", null, "PL", null, user);
        deliveryAddressRepo.save(deliveryAddress);

        Ingredient ingredient1 = TestUtils.ingredient("ac30a0fe-f97b-4b08-aaac-1309c76c5455", "Kurczak", true);
        ingredientRepo.save(ingredient1);
        Ingredient ingredient2 = TestUtils.ingredient("5632de5e-5c00-463b-b701-152fb76daea3", "Mąka", false);
        ingredientRepo.save(ingredient2);
        Ingredient ingredient3 = TestUtils.ingredient("5a1e100b-b82d-42bb-b34f-fc1c3497f9c8", "Kukurydza", true);
        ingredientRepo.save(ingredient3);
        Ingredient ingredient4 = TestUtils.ingredient("6b1e5fdc-d771-423e-b968-46e5a03a8200", "Ziemniak", false);
        ingredientRepo.save(ingredient4);
        Ingredient ingredient5 = TestUtils.ingredient("e7cec9d4-cdf3-4b32-b2f4-9b90f41a8d05", "Masło", false);
        ingredientRepo.save(ingredient5);
        Ingredient ingredient6 = TestUtils.ingredient("dc0ff645-2596-4c7d-a6fa-6926db65be16", "Marchew", false);
        ingredientRepo.save(ingredient6);
        Ingredient ingredient7 = TestUtils.ingredient("8ede3563-edd0-4d8b-b121-dd21dd11b7b6", "Jabłko", false);
        ingredientRepo.save(ingredient7);

        Product product1 = TestUtils.product("19e1281b-949d-48c3-9486-c0abbf7a0267", "Udko korczaka", ingredient1, ingredient2, ingredient3);
        productRepo.save(product1);
        Product product2 = TestUtils.product("e192ce71-75a9-4778-82c8-0bff97a98879", "Ziemniak", ingredient4, ingredient5);
        productRepo.save(product2);
        Product product3 = TestUtils.product("2af0b1a3-e2be-4e16-bc8b-b5ff4355b44a", "Surówka", ingredient6, ingredient7);
        productRepo.save(product3);

        Dish dish1 = TestUtils.dish("b9820ee0-c4b8-4ed8-90f7-7fa947f72548", 1, product1);
        dishRepo.save(dish1);
        Dish dish2 = TestUtils.dish("74e078d9-f49f-4c53-be67-f9f4cbe5804f", 3, product2);
        dishRepo.save(dish2);
        Dish dish3 = TestUtils.dish("0173a261-be83-4e9c-914f-eacfdaba46a8", 1, product3);
        dishRepo.save(dish3);

        MenuItem menuItem1 = TestUtils.menuItem("3deb5633-e968-4bdc-a114-cd4430a4f003", "Zestaw odbiadowy szefa kuchni", new BigDecimal("20.00"),
                VatTax._23, new BigDecimal("24.60"), restaurant, dish1, dish2, dish3);
        menuItemRepo.save(menuItem1);

        MenuItem menuItem2 = TestUtils.menuItem("44b2cd38-69eb-4178-b271-0bdef113eeea", "Powiększenie zestawu", new BigDecimal("8.00"),
                VatTax._23, new BigDecimal("9.84"), restaurant, dish1);
        menuItemRepo.save(menuItem2);

        OrderItem orderItem = TestUtils.orderItem("1f4fa012-cd0b-4ae0-bbc0-040d73d75b6c", 2, menuItem1);
        orderItemRepo.save(orderItem);

        Order order = TestUtils.order(STR_UUID, null, "Poproszę bez panierki", user,
                deliverer1, deliveryAddress, restaurant, new BigDecimal("40.00"), new BigDecimal("49.20"), new BigDecimal("49.20"),
                TestUtils.orderStatus("2020-01-01T15:00:00Z", true, null, null),
                orderItem
        );
        orderRepo.save(order);
        platformTransactionManager.commit(status1);

        TransactionStatus status2 = platformTransactionManager.getTransaction(TransactionDefinition.withDefaults());
        assertThrows(ResponseStatusException.class, () -> orderController.delete(UUID.fromString(STR_UUID)));
        platformTransactionManager.commit(status2);
    }
}