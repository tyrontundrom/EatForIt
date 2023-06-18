package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.enums.EvidenceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class OperationEvidenceDto {

    @NotNull
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EvidenceType evidenceType;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    private BigDecimal amount;

    @NotNull
    private UserDto userDto;
}
