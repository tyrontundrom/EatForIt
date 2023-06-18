package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.enums.Archive;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("employee")
class EmployeeDto {

    @NotNull
    private UUID uuid;

    @NotNull
    @Embedded
    private PersonalDataDto personalDataDto;

    @NotNull
    @Embedded
    private LogginDataDto logginDataDto;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;

   }
