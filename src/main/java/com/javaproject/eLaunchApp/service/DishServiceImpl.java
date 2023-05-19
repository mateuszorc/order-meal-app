package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.DishDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.Dish;
import com.javaproject.eLaunchApp.models.OperationEvidence;
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
public class DishServiceImpl implements DishService {

    private final DishRepo dishRepo;
    private final MenuItemRepo menuItemRepo;
    private final ProductRepo productRepo;

    @Autowired
    public DishServiceImpl(DishRepo dishRepo, MenuItemRepo menuItemRepo, ProductRepo productRepo) {
        this.dishRepo = dishRepo;
        this.menuItemRepo = menuItemRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<DishDTO> getAll() {
        return dishRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, DishDTO dishDTO) {

    }

    @Override
    public void delete(UUID uuid) {
        Dish dish = dishRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        dishRepo.delete(dish);
    }

    @Override
    public Optional<DishDTO> getByUuid(UUID uuid) {
        return dishRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }
}
