package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.RestaurantDTO;
import com.javaproject.eLaunchApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class restaurantServiceImpl implements RestaurantService {

    private final RestaurantRepo restaurantRepo;

    @Autowired
    public restaurantServiceImpl(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }


    @Override
    public List<RestaurantDTO> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, RestaurantDTO restaurantDTO) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<RestaurantDTO> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
