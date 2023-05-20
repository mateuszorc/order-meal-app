package com.javaproject.eLaunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.DTO.*;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.DishService;
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
@RequestMapping(path = "/api/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    interface DishListView extends DishDTO.View.Basic {}
    interface DishView extends DishDTO.View.Extended, ProductDTO.View.Extended, MenuItemDTO.View.Basic {}

    private final DishService dishService;

    interface DishDataUpdateValidation extends Default, DishDTO.DataUpdateValidation {}

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @JsonView(DishDataUpdateValidation.class)
    @GetMapping
    public List<DishDTO> get() {
        return dishService.getAll();
    }

    @JsonView(DishDataUpdateValidation.class)
    @GetMapping("/{uuid}")
    public DishDTO get(@PathVariable UUID uuid) {
        return dishService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Validated(DishDTO.DataUpdateValidation.class)
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody @Valid DishDTO dishDTO) {
        dishService.put(uuid, dishDTO);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        dishService.delete(uuid);
    }
}
