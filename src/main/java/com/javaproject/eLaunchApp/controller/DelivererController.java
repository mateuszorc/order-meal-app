package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.service.DelivererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(params = "/api/deliverers", produces = MediaType.APPLICATION_JSON_VALUE)
public class DelivererController {

    private final DelivererService delivererService;

    interface NewDelivererValidation extends Default, DelivererDTO.NewDelivererValidation {}

    @Autowired
    public DelivererController(DelivererService delivererService) {
        this.delivererService = delivererService;
    }

    @GetMapping
    public List<DelivererDTO> get() {
        return null;
    }

    @GetMapping("/{uuid}")
    public DelivererDTO get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @Validated(DelivererDTO.NewDelivererValidation.class)
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody DelivererDTO delivererDTO) {
        return;
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        return;
    }
}
