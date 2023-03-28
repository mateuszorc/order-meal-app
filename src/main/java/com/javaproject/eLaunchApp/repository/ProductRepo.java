package com.javaproject.eLaunchApp.repository;

import com.javaproject.eLaunchApp.models.Deliverer;
import com.javaproject.eLaunchApp.models.DeliveryAddress;
import com.javaproject.eLaunchApp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByUuid(UUID uuid);
}
