package com.javaproject.eLaunchApp.service;

import com.google.common.base.Objects;
import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.DeliveryAddressDTO;
import com.javaproject.eLaunchApp.models.DeliveryAddress;
import com.javaproject.eLaunchApp.models.DeliveryAddressBuilder;
import com.javaproject.eLaunchApp.models.User;
import com.javaproject.eLaunchApp.repository.DelivererRepo;
import com.javaproject.eLaunchApp.repository.DeliveryAddressRepo;
import com.javaproject.eLaunchApp.repository.OrderRepo;
import com.javaproject.eLaunchApp.repository.UserRepo;
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
        return deliveryAddressRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, DeliveryAddressDTO deliveryAddressDTO) {
        if (!Objects.equal(deliveryAddressDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByUuid(deliveryAddressDTO.getUser().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        DeliveryAddress deliveryAddress = deliveryAddressRepo.findByUuid(deliveryAddressDTO.getUuid())
                .orElseGet(() -> newDeliveryAddress(uuid, user));

        if (!Objects.equal(deliveryAddress.getUser().getUuid(), deliveryAddressDTO.getUser().getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        deliveryAddress.setDescription(deliveryAddressDTO.getDescription());
        deliveryAddress.setStreet(deliveryAddressDTO.getStreet());
        deliveryAddress.setStreetNumber(deliveryAddressDTO.getStreetNumber());
        deliveryAddress.setLocalNumber(deliveryAddressDTO.getLocalNumber());
        deliveryAddress.setPostcode(deliveryAddressDTO.getPostcode());
        deliveryAddress.setCity(deliveryAddressDTO.getCity());
        deliveryAddress.setBorough(deliveryAddressDTO.getBorough());
        deliveryAddress.setCounty(deliveryAddressDTO.getCounty());
        deliveryAddress.setState(deliveryAddressDTO.getState());

        if (deliveryAddress.getId() == null) {
            deliveryAddressRepo.save(deliveryAddress);
        }
    }

    @Override
    public void delete(UUID uuid) {
        DeliveryAddress deliveryAddress = deliveryAddressRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        deliveryAddressRepo.delete(deliveryAddress);
    }

    @Override
    public Optional<DeliveryAddressDTO> getByUuid(UUID uuid) {
        return deliveryAddressRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }

    private DeliveryAddress newDeliveryAddress(UUID uuid, User user) {
        return new DeliveryAddressBuilder()
                .withUuid(uuid)
                .withUser(user)
                .build();
    }
}
