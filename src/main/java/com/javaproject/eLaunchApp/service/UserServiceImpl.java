package com.javaproject.eLaunchApp.service;

import com.google.common.base.Objects;
import com.javaproject.eLaunchApp.DTO.UserDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.User;
import com.javaproject.eLaunchApp.models.UserBuilder;
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

import static com.javaproject.eLaunchApp.utils.ConvertUtils.convert;

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
        if (!Objects.equal(userDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByUuid(userDTO.getUuid())
                .orElseGet(() -> newUser(uuid));

        user.setPersonalData(convert(userDTO.getPersonalData()));
        user.setLoginData(convert(userDTO.getLoginData()));
        user.setArchive(userDTO.getArchive());

        if (user.getId() == null) {
            userRepo.save(user);
        }
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
        if(!Objects.equal(userDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        userRepo.findByUuid(userDTO.getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private User newUser(UUID uuid) {
        return new UserBuilder()
                .withUuid(uuid)
                .build();
    }
}
