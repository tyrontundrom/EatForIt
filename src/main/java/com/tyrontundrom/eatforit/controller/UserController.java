package com.tyrontundrom.eatforit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tyrontundrom.eatforit.dto.*;
import com.tyrontundrom.eatforit.events.OperationEvidenceCreator;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Validated
@Controller
@RequestMapping(path = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    interface UserListView extends UserDto.View.Basic, PersonalDataDto.View.Basic {
    }

    interface UserView extends UserDto.View.Extended, PersonalDataDto.View.Extended, LogginDataDto.View.Basic,
            DeliveryAddressDto.View.Basic, OperationEvidenceDto.View.Extended, DiscountCodeDto.View.Extended {
    }

    interface DataUpdateValidation extends Default, UserDto.DataUpdateValidation {
    }

    interface NewOperationValidation extends Default, UserDto.NewOperationValidation {
    }

    @JsonView(UserListView.class)
    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @JsonView(UserView.class)
    @GetMapping("{uuid}")
    public UserDto get(@PathVariable UUID uuid) {
        return userService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Validated(DataUpdateValidation.class)
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid UserDto userDto) {
        userService.put(uuid, userDto);
    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        userService.delete(uuid);
    }

    @Transactional
    @Validated(UserDto.NewOperationValidation.class)
    @PostMapping("{uuid}/new-operation")
    public void postOperation(@PathVariable UUID uuid, @RequestBody @Valid UserDto userDto) {
        userService.validateNewOperation(uuid, userDto);

        OperationEvidenceCreator operationEvidenceCreator = new OperationEvidenceCreator(this, userDto);
        applicationEventPublisher.publishEvent(operationEvidenceCreator);
    }

    @JsonView(UserView.class)
    @GetMapping("{uuid}/delivery-address")
    public List<DeliveryAddressDto> getUserAddresses(@PathVariable UUID uuid) {
        UserDto userDto = userService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return userDto.getDeliveryAddressDtos();
    }
}
