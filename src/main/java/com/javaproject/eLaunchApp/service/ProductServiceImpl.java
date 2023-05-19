package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.ProductDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.Product;
import com.javaproject.eLaunchApp.repository.*;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final IngredientRepo ingredientRepo;
    private final DishRepo dishRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, IngredientRepo ingredientRepo, DishRepo dishRepo) {
        this.productRepo = productRepo;
        this.ingredientRepo = ingredientRepo;
        this.dishRepo = dishRepo;
    }


    @Override
    public List<ProductDTO> getAll() {
        return productRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, ProductDTO productDTO) {

    }

    @Override
    public void delete(UUID uuid) {
        Product product = productRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        productRepo.delete(product);
    }

    @Override
    public Optional<ProductDTO> getByUuid(UUID uuid) {
        return productRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }
}