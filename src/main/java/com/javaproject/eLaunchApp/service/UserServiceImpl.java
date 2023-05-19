package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.UserDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.User;
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
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public List<UserDTO> getAll() {
        return userRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, UserDTO userDTO) {

    }

    @Override
    public void delete(UUID uuid) {
        User user = userRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userRepo.delete(user);
    }

    @Override
    public Optional<UserDTO> getByUuid(UUID uuid) {
        return userRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }

    @Override
    public void validateNewOperation(UUID uuid, UserDTO userDTO) {

    }
}
