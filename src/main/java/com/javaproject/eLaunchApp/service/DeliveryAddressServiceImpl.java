package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.DeliveryAddressDTO;
import com.javaproject.eLaunchApp.repository.DelivererRepo;
import com.javaproject.eLaunchApp.repository.DeliveryAddressRepo;
import com.javaproject.eLaunchApp.repository.OrderRepo;
import com.javaproject.eLaunchApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressRepo deliveryAddressRepo;
    private final UserRepo userRepo;

    @Autowired
    public DeliveryAddressServiceImpl(DeliveryAddressRepo deliveryAddressRepo, UserRepo userRepo) {
        this.deliveryAddressRepo = deliveryAddressRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<DeliveryAddressDTO> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, DeliveryAddressDTO deliveryAddressDTO) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<DeliveryAddressDTO> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
