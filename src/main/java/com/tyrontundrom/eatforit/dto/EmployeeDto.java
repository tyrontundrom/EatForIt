package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.enums.Archive;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PersonalDataDto getPersonalDataDto() {
        return personalDataDto;
    }

    public void setPersonalDataDto(PersonalDataDto personalDataDto) {
        this.personalDataDto = personalDataDto;
    }

    public LogginDataDto getLogginDataDto() {
        return logginDataDto;
    }

    public void setLogginDataDto(LogginDataDto logginDataDto) {
        this.logginDataDto = logginDataDto;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
