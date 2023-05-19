package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.DiscountCodeDTO;
import com.javaproject.eLaunchApp.models.Deliverer;
import com.javaproject.eLaunchApp.models.DeliveryAddress;
import com.javaproject.eLaunchApp.models.DiscountCode;
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
public class DiscountCodeServiceImpl implements DiscountCodeService {

    private final DiscountCodeRepo discountCodeRepo;
    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;

    @Autowired
    public DiscountCodeServiceImpl(DiscountCodeRepo discountCodeRepo, UserRepo userRepo, RestaurantRepo restaurantRepo) {
        this.discountCodeRepo = discountCodeRepo;
        this.userRepo = userRepo;
        this.restaurantRepo = restaurantRepo;
    }

    @Override
    public List<DiscountCodeDTO> getAll() {
        return discountCodeRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, DiscountCodeDTO discountCodeDTO) {
        DiscountCode discountCode= discountCodeRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        discountCodeRepo.delete(discountCode);
    }

    @Override
    public void delete(UUID uuid) {
        DiscountCode discountCode = discountCodeRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        discountCodeRepo.delete(discountCode);
    }

    @Override
    public Optional<DiscountCodeDTO> getByUuid(UUID uuid) {
        return discountCodeRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }
}
