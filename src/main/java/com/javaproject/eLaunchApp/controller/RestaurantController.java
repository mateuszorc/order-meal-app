package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.DTO.RestaurantDTO;
import com.javaproject.eLaunchApp.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(params = "/api/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    private final RestaurantService restaurantService;

    interface RestaurantDataUpdateValidation extends Default, RestaurantDTO.DataUpdateValidation {}

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantDTO> get() {
        return null;
    }

    @GetMapping("/{uuid}")
    public RestaurantDTO get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @Validated(RestaurantDataUpdateValidation.class)
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody RestaurantDTO restaurantDTO) {
        return;
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        return;
    }
}
