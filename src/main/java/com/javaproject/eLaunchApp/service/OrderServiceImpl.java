package com.javaproject.eLaunchApp.service;

import com.google.common.base.Objects;
import com.javaproject.eLaunchApp.DTO.*;
import com.javaproject.eLaunchApp.models.*;
import com.javaproject.eLaunchApp.models.enums.EvidenceType;
import com.javaproject.eLaunchApp.models.enums.PriceType;
import com.javaproject.eLaunchApp.repository.*;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;
    private final DelivererRepo delivererRepo;
    private final MenuItemRepo menuItemRepo;
    private final DiscountCodeRepo discountCodeRepo;
    private final OrderItemRepo orderItemRepo;
    private final OrderItemServiceImpl orderItemService;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, UserRepo userRepo, RestaurantRepo restaurantRepo,
                            DelivererRepo delivererRepo, MenuItemRepo menuItemRepo, DiscountCodeRepo discountCodeRepo,
                            OrderItemRepo orderItemRepo, OrderItemServiceImpl orderItemService) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.restaurantRepo = restaurantRepo;
        this.delivererRepo = delivererRepo;
        this.menuItemRepo = menuItemRepo;
        this.discountCodeRepo = discountCodeRepo;
        this.orderItemRepo = orderItemRepo;
        this.orderItemService = orderItemService;
    }


    @Override
    public List<OrderDTO> getAll() {
        return orderRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, OrderDTO orderDTO) {
        if (!Objects.equal(orderDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByUuid(orderDTO.getUser().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Restaurant restaurant = restaurantRepo.findByUuid(orderDTO.getRestaurantDTO().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Deliverer deliverer = delivererRepo.findByUuid(orderDTO.getDelivererDTO().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Order order = orderRepo.findByUuid(orderDTO.getUuid())
                .orElseGet(() -> newOrder(uuid, user, restaurant));

        if (!Objects.equal(orderDTO.getUser().getUuid(), orderDTO.getUser().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equal(orderDTO.getRestaurantDTO().getUuid(), orderDTO.getRestaurantDTO().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (orderDTO.getOrderStatusDTO().getDeliveryTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<OrderItem> orderItems = putOrderItems(orderDTO);
        DiscountCode discountCode = putDiscountCode(orderDTO);

        BigDecimal orderNettoPrice;
        BigDecimal orderBruttoPrice;
        BigDecimal amountToPayButto;

        try {
            orderNettoPrice = orderItemService.calculatePrice(orderItems, BigDecimal.ZERO, PriceType.NETTO);
            orderBruttoPrice = orderItemService.calculatePrice(orderItems, BigDecimal.ZERO, PriceType.BRUTTO);
            amountToPayButto = orderItemService.applyDiscount(discountCode, orderBruttoPrice);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.setNettoPrice(orderNettoPrice);
        order.setNote(orderDTO.getNote());
        order.setBruttoPrice(orderBruttoPrice);
        order.setAmountToPayBrutto(amountToPayButto);
        order.setDiscountCode(discountCode);
        order.setOrderItems(orderItems);
        order.setDeliverer(deliverer);

        if (order.getId() == null) {
            orderRepo.save(order);
        }
    }

    private DiscountCode putDiscountCode(OrderDTO orderDTO) {
        DiscountCode discountCode = null;
        if (orderDTO.getDiscountCodeDTO() != null) {
            discountCode = discountCodeRepo.findByUuid(orderDTO.getDiscountCodeDTO().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            if (discountCode.getRestaurants() != null) {
                discountCode.getRestaurants()
                        .stream()
                        .filter(restaurant -> restaurant.getUuid().equals(orderDTO.getRestaurantDTO().getUuid()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            }
            if (discountCode.getUsers() != null) {
                discountCode.getUsers()
                        .stream()
                        .filter(user -> user.getUuid().equals(orderDTO.getUser().getUuid()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            }
        }

        return discountCode;
    }

    private List<OrderItem> putOrderItems(OrderDTO orderDTO) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            MenuItem menuItem = menuItemRepo.findByUuid(orderItemDTO.getMenuItem().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            OrderItem orderItem = orderItemRepo.findByUuid(orderDTO.getUuid())
                    .orElseGet(() -> newOrderItem(orderDTO.getUuid()));

            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setMenuItem(menuItem);

            if (orderItem.getId() == null) {
                orderItemRepo.save(orderItem);
            }
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private OrderItem newOrderItem(UUID uuid) {
        return new OrderItemBuilder()
                .withUuid(uuid)
                .build();
    }

    private Order newOrder(UUID uuid, User user, Restaurant restuarant) {
        return new OrderBuilder()
                .withUuid(uuid)
                .withUser(user)
                .withRestaurant(restuarant)
                .withOrderStatus(newOrderStatus())
                .build();
    }

    private OrderStatus newOrderStatus() {
        return new OrderStatusBuilder()
                .withOrderTime(Instant.now())
                .withPaid(false)
                .build();
    }

    @Override
    public void delete(UUID uuid) {
        Order order = orderRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderRepo.delete(order);
    }

    @Override
    public Optional<OrderDTO> getByUuid(UUID uuid) {
        return orderRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }

    @Override
    public void setIsPaid(OrderDTO orderDTO) {
        Order order = orderRepo.findByUuid(orderDTO.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!order.getOrderStatus().getPaid()) {
            order.getOrderStatus().setPaid(true);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void setIsGivedOut(UUID uuid, OrderStatusDTO orderStatusDTO) {
        Order order = orderRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (order.getOrderStatus().getPaid() || order.getOrderStatus().getDeliveryTime() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.getOrderStatus().setGiveOutTime(orderStatusDTO.getGiveOutTime());
    }

    @Override
    public void setIsDelivered(UUID uuid, OrderStatusDTO orderStatusDTO) {
        Order order = orderRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (order.getOrderStatus().getPaid() || order.getOrderStatus().getGiveOutTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        order.getOrderStatus().setDeliveryTime(orderStatusDTO.getDeliveryTime());
    }

    @Override
    public UserDTO newOperationForPaidOrder(OrderDTO orderDTO) {
        User user = userRepo.findByUuid(orderDTO.getUser().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserDTO userDTO = ConvertUtils.convert(user);
        userDTO.setOperationEvidences(List.of(newEvidenceForOrderPayment(orderDTO)));
        return userDTO;
    }

    private OperationEvidenceDTO newEvidenceForOrderPayment(OrderDTO orderDTO) {
        return new OperationEvidenceDTOBuilder()
                .withDate(Instant.now())
                .withUser(orderDTO.getUser())
                .withAmount(orderDTO.getAmountToPayBrutto())
                .withType(EvidenceType.PAYMENT)
                .build();
    }
}
