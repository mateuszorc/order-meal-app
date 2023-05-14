package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.OrderDTO;
import com.javaproject.eLaunchApp.DTO.OrderStatusDTO;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.OrderService;
import com.javaproject.eLaunchApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(params = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;
    private final DelivererService delivererService;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    interface OrderDataUpdateValidation extends Default, OrderDTO.OrderValidation {}
    interface OrderStatusValidation extends Default, OrderDTO.OrderStatusValidation {}
    interface GiveOutValidation extends Default, OrderDTO.OrderStatusValidation, OrderStatusDTO.GiveOutStatusValidation {}
    interface DeliveryValidation extends Default, OrderDTO.OrderStatusValidation, OrderStatusDTO.DeliveryValidation {}

    @Autowired
    public OrderController(OrderService orderService, DelivererService delivererService, UserService userService,
                           ApplicationEventPublisher applicationEventPublisher) {
        this.orderService = orderService;
        this.delivererService = delivererService;
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping
    public List<OrderDTO> get() {
        return null;
    }

    @GetMapping("/{uuid}")
    public OrderDTO get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @Validated(OrderDataUpdateValidation.class)
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody OrderDTO orderDTO) {
        return;
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        return;
    }
}
