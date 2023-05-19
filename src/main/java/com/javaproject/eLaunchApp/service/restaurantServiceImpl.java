package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.RestaurantDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.Restaurant;
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
public class restaurantServiceImpl implements RestaurantService {

    private final RestaurantRepo restaurantRepo;

    @Autowired
    public restaurantServiceImpl(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }


    @Override
    public List<RestaurantDTO> getAll() {
        return restaurantRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, RestaurantDTO restaurantDTO) {

    }

    @Override
    public void delete(UUID uuid) {
        Restaurant restaurant = restaurantRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        restaurantRepo.delete(restaurant);
    }

    @Override
    public Optional<RestaurantDTO> getByUuid(UUID uuid) {
        return restaurantRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }
}
