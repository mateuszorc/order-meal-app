package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.UserDTO;
import com.javaproject.eLaunchApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public List<UserDTO> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, UserDTO userDTO) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<UserDTO> getByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void validateNewOperation(UUID uuid, UserDTO userDTO) {

    }
}
