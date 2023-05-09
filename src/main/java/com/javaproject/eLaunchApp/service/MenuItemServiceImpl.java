package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.MenuItemDTO;
import com.javaproject.eLaunchApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return null;
    }

    @Override
    public void put(UUID uuid, MenuItemDTO menuItemDTO) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<MenuItemDTO> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
