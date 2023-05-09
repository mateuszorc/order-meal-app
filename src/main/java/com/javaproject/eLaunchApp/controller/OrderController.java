package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.OrderDTO;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(params = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> get() {
        return null;
    }

    @GetMapping("/{uuid}")
    public OrderDTO get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
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
