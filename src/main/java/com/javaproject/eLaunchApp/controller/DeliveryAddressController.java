package com.javaproject.eLaunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.DTO.*;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(params = "/api/delivery-addresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryAddressController {
    interface DeliveryAddressListView extends DeliveryAddressDTO.View.Basic, UserDTO.View.Id {}
    interface DeliveryAddressView extends DeliveryAddressDTO.View.Extended, UserDTO.View.Id {}

    private final DeliveryAddressService deliveryAddressService;

    @Autowired
    public DeliveryAddressController(DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }

    @JsonView(DeliveryAddressListView.class)
    @GetMapping
    public List<DeliveryAddressDTO> get() {
        return deliveryAddressService.getAll();
    }

    @JsonView(DeliveryAddressView.class)
    @GetMapping("/{uuid}")
    public DeliveryAddressDTO get(@PathVariable UUID uuid) {
        return deliveryAddressService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody @Valid DeliveryAddressDTO deliveryAddressDTO) {
        deliveryAddressService.put(uuid, deliveryAddressDTO);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        deliveryAddressService.delete(uuid);
    }
}
