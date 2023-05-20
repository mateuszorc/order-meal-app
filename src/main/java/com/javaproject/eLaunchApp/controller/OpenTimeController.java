package com.javaproject.eLaunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.DTO.*;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.OpenTimeService;
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
@RequestMapping(path = "/api/open-times", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenTimeController {
    interface OpenTimeListView extends OpenTimeDTO.View.Basic {}
    interface OpenTimeView extends OpenTimeDTO.View.Extended, RestaurantDTO.View.Id {}

    private final OpenTimeService openTimeService;

    @Autowired
    public OpenTimeController(OpenTimeService openTimeService) {
        this.openTimeService = openTimeService;
    }

    @JsonView(OpenTimeListView.class)
    @GetMapping
    public List<OpenTimeDTO> get() {
        return openTimeService.getAll();
    }

    @JsonView(OpenTimeView.class)
    @GetMapping("/{uuid}")
    public OpenTimeDTO get(@PathVariable UUID uuid) {
        return openTimeService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PostMapping
    public void post(@RequestBody List<@Valid OpenTimeDTO> openTimeDTOS) {
        for (OpenTimeDTO openTimeDTO : openTimeDTOS) {
            put(openTimeDTO.getUuid(), openTimeDTO);
        }
    }

    @Transactional
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody @Valid OpenTimeDTO openTimeDTO) {
        openTimeService.put(uuid, openTimeDTO);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        openTimeService.delete(uuid);
    }
}
