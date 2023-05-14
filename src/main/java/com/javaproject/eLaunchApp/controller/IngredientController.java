package com.javaproject.eLaunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.DTO.*;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.IngredientService;
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
@RequestMapping(params = "/api/dingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {
    interface IngredientListView extends IngredientDTO.View.Basic {}
    interface IngredientView extends IngredientListView {}

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @JsonView(IngredientListView.class)
    @GetMapping
    public List<IngredientDTO> get() {
        return ingredientService.getAll();
    }

    @JsonView(IngredientView.class)
    @GetMapping("/{uuid}")
    public IngredientDTO get(@PathVariable UUID uuid) {
        return ingredientService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody @Valid IngredientDTO ingredientDTO) {
        ingredientService.put(uuid, ingredientDTO);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        ingredientService.delete(uuid);
    }
}
