package com.javaproject.eLaunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.DTO.*;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.DiscountCodeService;
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
@RequestMapping(path = "/api/discount-codes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DiscountCodeController {
    interface DiscountCodeListView extends DiscountCodeDTO.View.Basic, PeriodDTO.View.Basic {}
    interface DiscountCodeView extends DiscountCodeDTO.View.Extended, PeriodDTO.View.Basic {}

    private final DiscountCodeService discountCodeService;

    @Autowired
    public DiscountCodeController(DiscountCodeService discountCodeService) {
        this.discountCodeService = discountCodeService;
    }

    @JsonView(DiscountCodeListView.class)
    @GetMapping
    public List<DiscountCodeDTO> get() {
        return discountCodeService.getAll();
    }

    @JsonView(DiscountCodeView.class)
    @GetMapping("/{uuid}")
    public DiscountCodeDTO get(@PathVariable UUID uuid) {
        return discountCodeService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody @Valid DiscountCodeDTO discountCodeDTO) {
        discountCodeService.put(uuid, discountCodeDTO);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        delete(uuid);
    }
}
