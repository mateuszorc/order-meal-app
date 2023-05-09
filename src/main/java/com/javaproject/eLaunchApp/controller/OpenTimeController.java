package com.javaproject.eLaunchApp.controller;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.OpenTimeDTO;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.OpenTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(params = "/api/open-times", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenTimeController {

    private final OpenTimeService openTimeService;

    @Autowired
    public OpenTimeController(OpenTimeService openTimeService) {
        this.openTimeService = openTimeService;
    }

    @GetMapping
    public List<OpenTimeDTO> get() {
        return null;
    }

    @GetMapping("/{uuid}")
    public OpenTimeDTO get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody OpenTimeDTO openTimeDTO) {
        return;
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        return;
    }
}
