package com.javaproject.eLaunchApp.repository;

import com.javaproject.eLaunchApp.models.Deliverer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DelivererRepo extends JpaRepository<Deliverer, Long> {

    Optional<Deliverer> findByUuid(UUID uuid);
}
