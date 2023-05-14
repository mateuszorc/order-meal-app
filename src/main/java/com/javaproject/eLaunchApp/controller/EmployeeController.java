package com.javaproject.eLaunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javaproject.eLaunchApp.DTO.*;
import com.javaproject.eLaunchApp.service.DelivererService;
import com.javaproject.eLaunchApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(params = "/api/demployees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
    interface EmployeeListView extends EmployeeDTO.View.Basic, PersonalDataDTO.View.Basic {}
    interface EmployeeView extends EmployeeDTO.View.Extended, PersonalDataDTO.View.Extended, LoginDataDTO.View.Basic {}

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @JsonView(EmployeeListView.class)
    @GetMapping
    public List<EmployeeDTO> get() {
        return employeeService.getAll();
    }

    @JsonView(EmployeeView.class)
    @GetMapping("/{uuid}")
    public EmployeeDTO get(@PathVariable UUID uuid) {
        return employeeService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public  void put(@PathVariable UUID uuid, @RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.put(uuid, employeeDTO);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public  void delete(@PathVariable UUID uuid) {
        employeeService.delete(uuid);
    }
}
