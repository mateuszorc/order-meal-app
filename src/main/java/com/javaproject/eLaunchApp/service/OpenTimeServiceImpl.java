package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.OpenTimeDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.OpenTime;
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
public class OpenTimeServiceImpl implements OpenTimeService {

    private final OpenTimeRepo openTimeRepo;
    private final RestaurantRepo restaurantRepo;
    private final DishRepo dishRepo;

    @Autowired
    public OpenTimeServiceImpl(OpenTimeRepo openTimeRepo, RestaurantRepo restaurantRepo, DishRepo dishRepo) {
        this.openTimeRepo = openTimeRepo;
        this.restaurantRepo = restaurantRepo;
        this.dishRepo = dishRepo;
    }


    @Override
    public List<OpenTimeDTO> getAll() {
        return openTimeRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, OpenTimeDTO openTimeDTO) {

    }

    @Override
    public void delete(UUID uuid) {
        OpenTime openTime = openTimeRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        openTimeRepo.delete(openTime);
    }

    @Override
    public Optional<OpenTimeDTO> getByUuid(UUID uuid) {
        return openTimeRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }
}
