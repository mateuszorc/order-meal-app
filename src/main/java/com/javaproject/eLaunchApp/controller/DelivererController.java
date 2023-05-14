package com.javaproject.eLaunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.LoginDataDTO;
import com.javaproject.eLaunchApp.DTO.OrderDTO;
import com.javaproject.eLaunchApp.DTO.PersonalDataDTO;
import com.javaproject.eLaunchApp.service.DelivererService;
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
@RequestMapping(params = "/api/deliverers", produces = MediaType.APPLICATION_JSON_VALUE)
public class DelivererController {
    interface DelivererListView extends DelivererDTO.View.Basic, PersonalDataDTO.View.Basic {}
    interface DelivererView extends DelivererDTO.View.Extended, PersonalDataDTO.View.Extended, LoginDataDTO.View.Basic, OrderDTO.View.Extended {}

    private final DelivererService delivererService;

    interface NewDelivererValidation extends Default, DelivererDTO.NewDelivererValidation {}

    @Autowired
    public DelivererController(DelivererService delivererService) {
        this.delivererService = delivererService;
    }

    @JsonView(DelivererListView.class)
    @GetMapping
    public List<DelivererDTO> get() {
        return delivererService.getAll();
    }

    @JsonView(DelivererView.class)
    @GetMapping("/{uuid}")
    public DelivererDTO get(@PathVariable UUID uuid) {
        return delivererService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Validated(DelivererDTO.NewDelivererValidation.class)
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody @Valid DelivererDTO delivererDTO) {
        delivererService.put(uuid, delivererDTO);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        delivererService.delete(uuid);
    }
}
