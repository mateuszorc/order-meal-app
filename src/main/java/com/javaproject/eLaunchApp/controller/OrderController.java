package com.javaproject.eLaunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.DTO.*;
import com.javaproject.eLaunchApp.events.OperationEvidenceCreator;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.OrderService;
import com.javaproject.eLaunchApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(path = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    interface OrderListView extends OrderDTO.View.Basic, UserDTO.View.Id, DelivererDTO.View.Id, RestaurantDTO.View.Id {}
    interface OrderView extends OrderDTO.View.Extended, UserDTO.View.Id, DelivererDTO.View.Id, RestaurantDTO.View.Id {}

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

    @JsonView(OrderListView.class)
    @GetMapping
    public List<OrderDTO> get() {
        return orderService.getAll();
    }

    @JsonView(OrderListView.class)
    @GetMapping(params = {"user"})
    public List<OrderDTO> getByUser(@RequestParam("user") UUID userUuid) {
        UserDTO user = userService.getByUuid(userUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user.getOrderDTOS();
    }

    @JsonView(OrderListView.class)
    @GetMapping(params = {"deliverer"})
    public List<OrderDTO> getByDeliverer(@RequestParam("delivererUuid") UUID delivererUuid) {
        DelivererDTO deliverer = delivererService.getByUuid(delivererUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return deliverer.getOrders();
    }

    @JsonView(OrderView.class)
    @GetMapping("/{uuid}")
    public OrderDTO get(@PathVariable UUID uuid) {
        return orderService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Validated(OrderDataUpdateValidation.class)
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody @Valid OrderDTO orderDTO) {
        orderService.put(uuid, orderDTO);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        orderService.delete(uuid);
    }

    @Transactional
    @Validated(OrderStatusValidation.class)
    @PatchMapping("/{uuid}/paid")
    public void patchIsPaid(@PathVariable UUID uuid) {
        OrderDTO orderDTO = orderService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderService.setIsPaid(orderDTO);

        OperationEvidenceCreator operationEvidenceCreator =
                new OperationEvidenceCreator(this, orderService.newOperationForPaidOrder(orderDTO));
        applicationEventPublisher.publishEvent(operationEvidenceCreator);
    }

    @Transactional
    @Validated(GiveOutValidation.class)
    @PatchMapping("/{uuid}/given-out")
    public void patchIsGivenOut(@PathVariable UUID uuid, @RequestBody @Valid OrderStatusDTO orderStatusDTO) {
        orderService.setIsGivedOut(uuid, orderStatusDTO);
    }

    @Transactional
    @Validated(DeliveryValidation.class)
    @PatchMapping("/{uuid}/delivered")
    public void patchIsPDelivered(@PathVariable UUID uuid, @RequestBody @Valid OrderStatusDTO orderStatusDTO) {
        orderService.setIsDelivered(uuid, orderStatusDTO);
    }
}
