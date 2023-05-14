package com.javaproject.eLaunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.DTO.*;
import com.javaproject.eLaunchApp.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(params = "/api/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    interface RestaurantListView extends RestaurantDTO.View.Basic {}
    interface RestaurantView extends RestaurantDTO.View.Extended, LoginDataDTO.View.Basic, CompanyDataDTO.View.Extended, OpenTimeDTO.View.Extended {}

    private final RestaurantService restaurantService;

    interface RestaurantDataUpdateValidation extends Default, RestaurantDTO.DataUpdateValidation {}

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @JsonView(RestaurantListView.class)
    @GetMapping
    public List<RestaurantDTO> get() {
        return restaurantService.getAll();
    }

    @JsonView(RestaurantView.class)
    @GetMapping("/{uuid}")
    public RestaurantDTO get(@PathVariable UUID uuid) {
        return restaurantService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Validated(RestaurantDataUpdateValidation.class)
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody @Valid RestaurantDTO restaurantDTO) {
        restaurantService.put(uuid, restaurantDTO);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        restaurantService.delete(uuid);
    }
}
