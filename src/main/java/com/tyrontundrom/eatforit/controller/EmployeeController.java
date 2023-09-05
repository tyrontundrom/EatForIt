package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class EmployeeController {

    private final EmployeeService employeeService;

    interface EmployeeListView extends EmployeeDto.View.Basic, PersonalDataDto.View.Basic {}
    interface EmployeeView extends EmployeeDto.View.Extended, PersonalDataDto.View.Extended,
            LogginDataDto.View.Basic {}

    @JsonView(EmployeeListView.class)
    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeService.getAll();
    }

    @JsonView(EmployeeView.class)
    @GetMapping("{uuid}")
    public EmployeeDto get(@PathVariable UUID uuid) {
        return employeeService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid EmployeeDto employeeDto) {
        employeeService.put(uuid, employeeDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        employeeService.delete(uuid);
    }
}
