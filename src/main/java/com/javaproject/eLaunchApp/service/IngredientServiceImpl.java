package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.IngredientDTO;
import com.javaproject.eLaunchApp.repository.DelivererRepo;
import com.javaproject.eLaunchApp.repository.IngredientRepo;
import com.javaproject.eLaunchApp.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepo ingredientRepo;

    public IngredientServiceImpl(IngredientRepo ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }


    @Override
    public List<IngredientDTO> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, IngredientDTO ingredientDTO) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<IngredientDTO> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
