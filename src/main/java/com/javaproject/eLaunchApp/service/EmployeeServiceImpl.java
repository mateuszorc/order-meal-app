package com.javaproject.eLaunchApp.service;

import com.google.common.base.Objects;
import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.DTO.EmployeeDTO;
import com.javaproject.eLaunchApp.models.DiscountCode;
import com.javaproject.eLaunchApp.models.Employee;
import com.javaproject.eLaunchApp.models.EmployeeBuilder;
import com.javaproject.eLaunchApp.repository.DelivererRepo;
import com.javaproject.eLaunchApp.repository.EmployeeRepo;
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

import static com.javaproject.eLaunchApp.utils.ConvertUtils.convert;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }


    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepo.findAll()
                .stream()
                .map(ConvertUtils::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void put(UUID uuid, EmployeeDTO employeeDTO) {
        if (!Objects.equal(employeeDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Employee employee = employeeRepo.findByUuid(employeeDTO.getUuid())
                .orElseGet(() -> newEmployee(uuid));

        employee.setPersonalData(convert(employeeDTO.getPersonalData()));
        employee.setLoginData(convert(employeeDTO.getLoginData()));
        employee.setArchive(employeeDTO.getArchive());

        if (employee.getId() == null) {
            employeeRepo.save(employee);
        }
    }

    @Override
    public void delete(UUID uuid) {
        Employee employee = employeeRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        employeeRepo.delete(employee);
    }

    @Override
    public Optional<EmployeeDTO> getByUuid(UUID uuid) {
        return employeeRepo.findByUuid(uuid).map(ConvertUtils::convert);
    }

    private Employee newEmployee(UUID uuid) {
        return new EmployeeBuilder()
                .withUuid(uuid)
                .build();
    }
}
