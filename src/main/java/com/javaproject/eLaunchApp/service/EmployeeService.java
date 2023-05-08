package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.EmployeeDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {
	List<EmployeeDTO> getAll();
	void put(UUID uuid, EmployeeDTO employeeDTO);
	void delete(UUID uuid);
	Optional<EmployeeDTO> getByUuid(UUID uuid);
}
