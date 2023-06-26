package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
public class EmployeeDto {

    public static class View {
        public interface Id {}
        public interface Basic extends Id {}

        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotNull
    @Embedded
    private PersonalDataDto personalDataDto;

    @JsonView(View.Extended.class)
    @NotNull
    @Embedded
    private LogginDataDto logginDataDto;

    @JsonView(View.Extended.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;

   }
