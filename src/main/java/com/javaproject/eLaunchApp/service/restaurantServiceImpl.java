package com.javaproject.eLaunchApp.service;

import com.google.common.base.Objects;
import com.javaproject.eLaunchApp.DTO.RestaurantDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.Restaurant;
import com.javaproject.eLaunchApp.models.RestaurantBuilder;
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

import static com.javaproject.eLaunchApp.utils.ConvertUtils.*;

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
        if (!Objects.equal(restaurantDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Restaurant restaurant = restaurantRepo.findByUuid(restaurantDTO.getUuid())
                .orElseGet(() -> newRestaurant(uuid));

        restaurant.setName(restaurantDTO.getName());
        restaurant.setLoginData(convert(restaurantDTO.getLoginData()));
        restaurant.setCompanyData(convert(restaurantDTO.getCompanyData()));
        restaurant.setOpenTimes(convertOpenTimeDTOS(restaurantDTO.getOpenTimes()));
        restaurant.setMenuItems(convertMenuItemDTOS(restaurantDTO.getMenuItems()));
        restaurant.setDiscountCodes(convertDiscountCodeDTOS(restaurantDTO.getDiscountCodes()));
        restaurant.setArchive(restaurantDTO.getArchive());

        if (restaurant.getId() == null) {
            restaurantRepo.save(restaurant);
        }
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

    private Restaurant newRestaurant(UUID uuid) {
        return new RestaurantBuilder()
                .withUuid(uuid)
                .build();
    }
}
