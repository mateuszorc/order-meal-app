package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.DishDTO;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.DishService;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(params = "/api/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    private final DishService dishService;

    @GetMapping
    public List<DishDTO> get() {
        return null;
    }

    @GetMapping("/{uuid}")
    public DishDTO get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody DishDTO delivererDTO) {
        return;
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        return;
    }
}
