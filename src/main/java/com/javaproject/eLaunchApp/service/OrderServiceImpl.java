package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.OrderDTO;
import com.javaproject.eLaunchApp.DTO.OrderStatusDTO;
import com.javaproject.eLaunchApp.DTO.UserDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.Order;
import com.javaproject.eLaunchApp.repository.*;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, UserRepo userRepo, RestaurantRepo restaurantRepo,
                            DelivererRepo delivererRepo, MenuItemRepo menuItemRepo, DiscountCodeRepo discountCodeRepo,
                            OrderItemRepo orderItemRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.restaurantRepo = restaurantRepo;
        this.delivererRepo = delivererRepo;
        this.menuItemRepo = menuItemRepo;
        this.discountCodeRepo = discountCodeRepo;
        this.orderItemRepo = orderItemRepo;
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

    }

    @Override
    public void setIsGivedOut(UUID uuid, OrderStatusDTO orderStatusDTO) {

    }

    @Override
    public void setIsDelivered(UUID uuid, OrderStatusDTO orderStatusDTO) {

    }

    @Override
    public UserDTO newOperationForPaidOrder(OrderDTO orderDTO) {
        return null;
    }
}
