package com.javaproject.eLaunchApp.service;

import com.google.common.base.Objects;
import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.IngredientDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.Ingredient;
import com.javaproject.eLaunchApp.models.IngredientBuilder;
import com.javaproject.eLaunchApp.repository.DelivererRepo;
import com.javaproject.eLaunchApp.repository.IngredientRepo;
import com.javaproject.eLaunchApp.repository.OrderRepo;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepo ingredientRepo;

    public IngredientServiceImpl(IngredientRepo ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }


    @Override
    public List<IngredientDTO> getAll() {
        return ingredientRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, IngredientDTO ingredientDTO) {
        if (!Objects.equal(ingredientDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Ingredient ingredient = ingredientRepo.findByUuid(ingredientDTO.getUuid())
                .orElseGet(() -> newIngredient(uuid));

        ingredient.setName(ingredientDTO.getName());
        ingredient.setAllergen(ingredientDTO.getAllergen());

        if (ingredient.getId() == null) {
            ingredientRepo.save(ingredient);
        }
    }

    @Override
    public void delete(UUID uuid) {
        Ingredient ingredient = ingredientRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ingredientRepo.delete(ingredient);
    }

    @Override
    public Optional<IngredientDTO> getByUuid(UUID uuid) {
        return ingredientRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }

    private Ingredient newIngredient(UUID uuid) {
        return new IngredientBuilder()
                .withUuid(uuid)
                .build();
    }
}
