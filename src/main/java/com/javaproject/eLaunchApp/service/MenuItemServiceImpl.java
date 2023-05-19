package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.MenuItemDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.MenuItem;
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
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepo menuItemRepo;
    private final RestaurantRepo restaurantRepo;
    private final DishRepo dishRepo;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepo menuItemRepo, RestaurantRepo restaurantRepo, DishRepo dishRepo) {
        this.menuItemRepo = menuItemRepo;
        this.restaurantRepo = restaurantRepo;
        this.dishRepo = dishRepo;
    }


    @Override
    public List<MenuItemDTO> getAll() {
        return menuItemRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, MenuItemDTO menuItemDTO) {

    }

    @Override
    public void delete(UUID uuid) {
        MenuItem menuItem = menuItemRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        menuItemRepo.delete(menuItem);
    }

    @Override
    public Optional<MenuItemDTO> getByUuid(UUID uuid) {
        return menuItemRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }
}
