package com.javaproject.eLaunchApp.service;

import com.google.common.base.Objects;
import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.DishDTO;
import com.javaproject.eLaunchApp.DTO.MenuItemDTO;
import com.javaproject.eLaunchApp.models.*;
import com.javaproject.eLaunchApp.repository.*;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
        if (!Objects.equal(menuItemDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Restaurant restaurant = restaurantRepo.findByUuid(menuItemDTO.getRestaurants().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Dish> dishes = new ArrayList<>();
        for (DishDTO d : menuItemDTO.getDishes()) {
            Dish dish = dishRepo.findByUuid(d.getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            dishes.add(dish);
        }

        MenuItem menuItem = menuItemRepo.findByUuid(menuItemDTO.getUuid())
                .orElseGet(() -> newMenuItem(uuid, restaurant));

        menuItem.setName(menuItemDTO.getName());
        menuItem.setNettoPrice(menuItemDTO.getNettoPrice());
        menuItem.setVatTax(menuItemDTO.getVatTax());
        menuItem.setBruttoPrice(menuItemDTO.getBruttoPrice());
        menuItem.setDishes(dishes);

        if (menuItem.getId() == null) {
            menuItemRepo.save(menuItem);
        }
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

    private MenuItem newMenuItem(UUID uuid, Restaurant restaurant) {
        return new MenuItemBuilder()
                .withUuid(uuid)
                .withRestaurants(restaurant)
                .build();
    }
}
