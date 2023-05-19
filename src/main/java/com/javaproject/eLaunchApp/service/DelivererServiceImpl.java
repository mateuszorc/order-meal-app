package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.models.Deliverer;
import com.javaproject.eLaunchApp.repository.DelivererRepo;
import com.javaproject.eLaunchApp.repository.OrderRepo;
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
public class DelivererServiceImpl implements DelivererService {

    private final DelivererRepo delivererRepo;
    private final OrderRepo orderRepo;

    @Autowired
    public DelivererServiceImpl(DelivererRepo delivererRepo, OrderRepo orderRepo) {
        this.delivererRepo = delivererRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    public List<DelivererDTO> getAll() {
        return delivererRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, DelivererDTO delivererDTO) {

    }

    @Override
    public void delete(UUID uuid) {
        Deliverer deliverer = delivererRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        delivererRepo.delete(deliverer);
    }

    @Override
    public Optional<DelivererDTO> getByUuid(UUID uuid) {
        return delivererRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }
}
